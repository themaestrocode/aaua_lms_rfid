package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.Event;
import com.themaestrocode.aaualms.repository.EventRepository;

import java.util.List;

public class EventService {
    EventRepository eventRepository = new EventRepository();

    public List<Event> getTodaysEvents() {
        return eventRepository.getTodaysEvents();
    }
}
