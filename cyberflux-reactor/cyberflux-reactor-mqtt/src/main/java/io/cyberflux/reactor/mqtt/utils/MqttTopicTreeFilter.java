package io.cyberflux.reactor.mqtt.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;

public final class MqttTopicTreeFilter implements MqttTopicFilter {
    private final TopicTreeNode rootNode;
    private final LongAdder topicNumber;

    public MqttTopicTreeFilter() {
        rootNode = new TopicTreeNode(null);
        topicNumber = new LongAdder();
    }

    @Override
    public Set<MqttSubscriptionStore> getTopicStore(String topic) {
        return rootNode.getTopicStore(topic);
    }

    @Override
    public Set<MqttSubscriptionStore> getAllTopicStore() {
        return rootNode.getAllTopicStore();
    }

    @Override
    public boolean appendTopicStore(MqttSubscriptionStore store) {
        if (rootNode.appendTopicStore(store)) {
			store.linkChannel();
            topicNumber.add(+1);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTopicStore(MqttSubscriptionStore store) {
        if(rootNode.removeTopicStore(store)) {
			store.unlinkChannel();
            topicNumber.add(-1);
            return true;
        }
        return false;
    }

    @Override
    public long count() {
       return topicNumber.sum();
    }

    private final class TopicTreeNode {
        @SuppressWarnings("unused")
        private final String nodeName;
        private final Set<MqttSubscriptionStore> topicStores;
        private final Cache<String, TopicTreeNode> childNodes;

        public TopicTreeNode(String nodeName) {
            this.nodeName = nodeName;
            topicStores = new CopyOnWriteArraySet<>();
            childNodes = Caffeine.newBuilder().build();
        }

        private List<MqttSubscriptionStore> searchTopics(TopicTreeNode node, String[] topics, int index) {
            LinkedList<MqttSubscriptionStore> topicStoreList = new LinkedList<>();
            String lastTopic = topics[index];
            TopicTreeNode multiNode = node.childNodes.getIfPresent(MqttSubscriptionStore.MULTI_WILDCARD);
            if(multiNode != null) {
                topicStoreList.addAll(multiNode.topicStores);
            }
            if(index < topics.length) {
                TopicTreeNode singleNode = node.childNodes.getIfPresent(MqttSubscriptionStore.SINGLE_WILDCARD);
                if(singleNode != null) {
                    topicStoreList.addAll(searchTopics(singleNode, topics, index + 1));
                }
                TopicTreeNode childNode = node.childNodes.getIfPresent(lastTopic);
                if(childNode != null) {
                    topicStoreList.addAll(searchTopics(node, topics, index + 1));
                }
            } else {
                TopicTreeNode childNode = node.childNodes.getIfPresent(lastTopic);
                if(childNode != null && childNode.topicStores.size() > 0) {
                    topicStoreList.addAll(childNode.topicStores);
                }
                childNode = node.childNodes.getIfPresent(MqttSubscriptionStore.SINGLE_WILDCARD);
                if (childNode != null && childNode.topicStores.size() > 0) {
                    topicStoreList.addAll(childNode.topicStores);
                }
            }
            return topicStoreList;
        }

        private List<MqttSubscriptionStore> searchTopics(TopicTreeNode node) {
            LinkedList<MqttSubscriptionStore> topicStoreList = new LinkedList<>();
            topicStoreList.addAll(node.topicStores);
            topicStoreList.addAll(
                node.childNodes.asMap().values().stream()
                    .flatMap(n -> n.searchTopics(n).stream())
                    .collect(Collectors.toSet())
            );
            return topicStoreList;
        }

        private boolean saveNode(MqttSubscriptionStore store, String[] topics, int index) {
            TopicTreeNode childNode = childNodes.get(topics[index], key -> {
                return new TopicTreeNode(key);
            });
            if(index < topics.length) {
                return childNode.saveNode(store, topics, index);
            } else {
                return topicStores.add(store);
            }
        }

        public Set<MqttSubscriptionStore> getTopicStore(String topic) {
            String[] topics = topic.split(MqttSubscriptionStore.SEPARATOR);
            return searchTopics(this, topics, 0).stream().collect(Collectors.toSet());
        }

        public Set<MqttSubscriptionStore> getAllTopicStore() {
            return searchTopics(this).stream().collect(Collectors.toSet());
        }

        public boolean appendTopicStore(MqttSubscriptionStore store) {
            String[] topics = store.getTopic().split(MqttSubscriptionStore.SEPARATOR);
            return saveNode(store, topics, 0);
        }

        public boolean removeTopicStore(MqttSubscriptionStore store) {
            TopicTreeNode node = this;
            String[] topics = store.getTopic().split(MqttSubscriptionStore.SEPARATOR);
            for (String topic : topics) {
                if(node == null) continue;
                node = node.childNodes.getIfPresent(topic);
            }
            Set<MqttSubscriptionStore> topicSet = node.topicStores;
            return topicSet == null ? false : topicSet.remove(store);
        }
    }
}
