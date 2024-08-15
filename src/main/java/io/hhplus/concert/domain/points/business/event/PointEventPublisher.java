package io.hhplus.concert.domain.points.business.event;

public interface PointEventPublisher {
    void savePointHistory(PointEvent pointEvent);
}
