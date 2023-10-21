let source: EventSource;

export const EngineAddedEvent = 'engine-added';
export const EngineLeavingEvent = 'engine-leaving';
export const EngineRemoveEvent = 'engine-remove';
export const EngineUpdateEvent = 'engine-update';
export const EngineLinkEvent = 'engine-link';
export const LogUpdateEvent = 'log-update';

export const EngineEventTable = [
  EngineAddedEvent,
  EngineLeavingEvent,
  EngineRemoveEvent,
  EngineUpdateEvent,
  EngineLinkEvent,
];

export function useGatewayEventSource(): EventSource {
  if (!source) {
    source = new EventSource("gateway/sse");
    source.onopen = (event) => {
      console.log("SSE: gateway/sse OK.", event);
    };
    source.onerror = (event) => {
      console.log("SSE: gateway/sse NO.", event);
    };
  }
  return source;
}
