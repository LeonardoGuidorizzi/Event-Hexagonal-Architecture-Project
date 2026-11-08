package dev.devdreamer.Event.domain;

import dev.devdreamer.Event.domain.enums.EventStatus;
import dev.devdreamer.Event.domain.enums.EventType;

import java.time.LocalDateTime;
import java.util.Random;

public class Event {
    String id;
    String name;
    String description;
    String identifier;
    String local;
    String host;
    Integer capacity;
    Integer registeredCount ;
    EventType eventType;
    EventStatus status;
    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Event() {
    }

    public Event(
            String id,
            String name,
            String description,
            String identifier,
            String local,
            String host,
            Integer registeredCount ,
            Integer capacity,
            EventType eventType,
            EventStatus status,
            LocalDateTime start,
            LocalDateTime end,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.identifier = identifier;
        this.local = local;
        this.host = host;
        this.registeredCount  = registeredCount ;
        this.capacity = capacity;
        this.eventType = eventType;
        this.status = status;
        this.start = start;
        this.end = end;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Event create(
            String name,
            String description,
            String local,
            String host,
            Integer capacity,
            LocalDateTime start,
            LocalDateTime end,
            EventType eventType
            ){
        validate(
                name,
                description,
                local,
                host,
                capacity,
                eventType,
                start,
                end);
        Event event = new Event();
        event.name = name;
        event.description = description;
        event.identifier = generateIdentifier();
        event.local = local;
        event.host = host;
        event.capacity = capacity;
        event.registeredCount = 0;
        event.eventType = eventType;
        event.status = EventStatus.DRAFT;
        event.start = start;
        event.end = end;
        return event;

    }
    public void publish (){
        if(this.status != EventStatus.DRAFT){
            throw  new IllegalStateException("Voce não pode publicar um evento se ele não estiver DRAFTADO");
        }
        this.status = EventStatus.PUBLISHED;
    }

    public void cancel (){
    if(this.status == EventStatus.CANCELLED){
        throw new IllegalArgumentException("Esse Evento já esta cancelado");
    }
    this.status = EventStatus.CANCELLED;
    }

    public void registerParticipant(){
        if(this.status != EventStatus.PUBLISHED){
            throw new IllegalArgumentException("Inscrições só são permitidas em publicação");
        }
        if(this.registeredCount  > this.capacity ){
            throw new IllegalArgumentException("Limite excedido, não é possivel adicionar mais participantes");
        }
        this.registeredCount ++;
    }

    public boolean hasAvailableSlots() {
        return this.registeredCount < this.capacity;
    }

    public int availableSlots() {
        return this.capacity - this.registeredCount;
    }

    public static void validate(String name, String description,  String local, String host, Integer capacity, EventType eventType, LocalDateTime start, LocalDateTime end){
     if(name == null || name.isBlank()){
         throw new IllegalArgumentException("O nome do Evento é obrigatorio");
     }
     if(description == null || description.isBlank()){
         throw new IllegalArgumentException("A descrição do Evento é obrigatorio");
     }
     if(local == null || local.isBlank()){
         throw new IllegalArgumentException("O local do Evento é obrigatorio");
     }
     if(host == null || host.isBlank()){
         throw  new IllegalArgumentException("O nome do dono deve ser informado");
     }
     if(eventType == null){
         throw new  IllegalArgumentException("O tipo do Evento deve ser informado");
     }
     if(capacity == null || capacity <= 0){
         throw new IllegalArgumentException("A capacidade do Evento precisa ser maior que zero");
     }
     if(!end.isAfter(start)){
         throw new IllegalArgumentException("Data de fim deve ser posterior à data de início.");
     }
    }

    public static String generateIdentifier(){
         return String.valueOf(10000 + new Random().nextInt(10000));
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLocal() {
        return local;
    }

    public String getHost() {
        return host;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getRegisteredCount () {
        return registeredCount ;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventStatus getStatus() {
        return status;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setRegisteredCount (Integer registeredCount ) {
        this.registeredCount  = registeredCount ;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
