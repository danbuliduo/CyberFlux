let source: EventSource;


export const MetricUpdateEvent = 'metric-update';

export function useEngineEventSource(): EventSource {
  if (!source) {
    source = new EventSource("engine/sse");
    source.onopen = (event) => {
      console.log("SSE: engine/sse OK.", event);
    };
    source.onerror = (event) => {
      console.log("SSE: engine/sse NO.", event);
    };
  }
  return source;
}
