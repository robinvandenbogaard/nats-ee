package nl.robinthedev.nats.annotation;

import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.nats.client.ConsumerContext;
import io.nats.client.Dispatcher;
import io.nats.client.JetStream;
import io.nats.client.JetStreamManagement;
import io.nats.client.JetStreamOptions;
import io.nats.client.KeyValue;
import io.nats.client.KeyValueManagement;
import io.nats.client.KeyValueOptions;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.ObjectStore;
import io.nats.client.ObjectStoreManagement;
import io.nats.client.ObjectStoreOptions;
import io.nats.client.Options;
import io.nats.client.Statistics;
import io.nats.client.StreamContext;
import io.nats.client.Subscription;
import io.nats.client.api.ServerInfo;
import io.nats.client.impl.Headers;
import java.net.InetAddress;
import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

class TestPublishConnection implements Connection {
  private String subject;
  private byte[] body;
  private boolean invoked;

  boolean isInvoked() {
    return invoked;
  }

  PublishParameters getPublishParameters() {
    return new PublishParameters(subject, body);
  }

  @Override
  public void publish(String subject, byte[] body) {
    this.subject = subject;
    this.body = body;
    this.invoked = true;
  }

  @Override
  public void publish(String subject, Headers headers, byte[] body) {}

  @Override
  public void publish(String subject, String replyTo, byte[] body) {}

  @Override
  public void publish(String subject, String replyTo, Headers headers, byte[] body) {}

  @Override
  public void publish(Message message) {}

  @Override
  public CompletableFuture<Message> request(String subject, byte[] body) {
    return null;
  }

  @Override
  public CompletableFuture<Message> request(String subject, Headers headers, byte[] body) {
    return null;
  }

  @Override
  public CompletableFuture<Message> requestWithTimeout(
      String subject, byte[] body, Duration timeout) {
    return null;
  }

  @Override
  public CompletableFuture<Message> requestWithTimeout(
      String subject, Headers headers, byte[] body, Duration timeout) {
    return null;
  }

  @Override
  public CompletableFuture<Message> request(Message message) {
    return null;
  }

  @Override
  public CompletableFuture<Message> requestWithTimeout(Message message, Duration timeout) {
    return null;
  }

  @Override
  public Message request(String subject, byte[] body, Duration timeout) {
    return null;
  }

  @Override
  public Message request(String subject, Headers headers, byte[] body, Duration timeout) {
    return null;
  }

  @Override
  public Message request(Message message, Duration timeout) {
    return null;
  }

  @Override
  public Subscription subscribe(String subject) {
    return null;
  }

  @Override
  public Subscription subscribe(String subject, String queueName) {
    return null;
  }

  @Override
  public Dispatcher createDispatcher(MessageHandler handler) {
    return null;
  }

  @Override
  public Dispatcher createDispatcher() {
    return null;
  }

  @Override
  public void closeDispatcher(Dispatcher dispatcher) {}

  @Override
  public void addConnectionListener(ConnectionListener connectionListener) {}

  @Override
  public void removeConnectionListener(ConnectionListener connectionListener) {}

  @Override
  public void flush(Duration timeout) {}

  @Override
  public CompletableFuture<Boolean> drain(Duration timeout) {
    return null;
  }

  @Override
  public void close() {}

  @Override
  public Status getStatus() {
    return null;
  }

  @Override
  public long getMaxPayload() {
    return 0;
  }

  @Override
  public Collection<String> getServers() {
    return null;
  }

  @Override
  public Statistics getStatistics() {
    return null;
  }

  @Override
  public Options getOptions() {
    return null;
  }

  @Override
  public ServerInfo getServerInfo() {
    return null;
  }

  @Override
  public String getConnectedUrl() {
    return null;
  }

  @Override
  public InetAddress getClientInetAddress() {
    return null;
  }

  @Override
  public String getLastError() {
    return null;
  }

  @Override
  public void clearLastError() {}

  @Override
  public String createInbox() {
    return null;
  }

  @Override
  public void flushBuffer() {}

  @Override
  public Duration RTT() {
    return null;
  }

  @Override
  public StreamContext getStreamContext(String streamName) {
    return null;
  }

  @Override
  public StreamContext getStreamContext(String streamName, JetStreamOptions options) {
    return null;
  }

  @Override
  public ConsumerContext getConsumerContext(String streamName, String consumerName) {
    return null;
  }

  @Override
  public ConsumerContext getConsumerContext(
      String streamName, String consumerName, JetStreamOptions options) {
    return null;
  }

  @Override
  public JetStream jetStream() {
    return null;
  }

  @Override
  public JetStream jetStream(JetStreamOptions options) {
    return null;
  }

  @Override
  public JetStreamManagement jetStreamManagement() {
    return null;
  }

  @Override
  public JetStreamManagement jetStreamManagement(JetStreamOptions options) {
    return null;
  }

  @Override
  public KeyValue keyValue(String bucketName) {
    return null;
  }

  @Override
  public KeyValue keyValue(String bucketName, KeyValueOptions options) {
    return null;
  }

  @Override
  public KeyValueManagement keyValueManagement() {
    return null;
  }

  @Override
  public KeyValueManagement keyValueManagement(KeyValueOptions options) {
    return null;
  }

  @Override
  public ObjectStore objectStore(String bucketName) {
    return null;
  }

  @Override
  public ObjectStore objectStore(String bucketName, ObjectStoreOptions options) {
    return null;
  }

  @Override
  public ObjectStoreManagement objectStoreManagement() {
    return null;
  }

  @Override
  public ObjectStoreManagement objectStoreManagement(ObjectStoreOptions options) {
    return null;
  }
}
