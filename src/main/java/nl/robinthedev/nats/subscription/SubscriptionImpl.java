package nl.robinthedev.nats.subscription;

record SubscriptionImpl(String subject, PayloadMethod payloadMethod) implements Subscription {}
