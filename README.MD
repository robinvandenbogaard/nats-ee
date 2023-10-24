# Nats EE

If you want to ease your Nats integration in a Java EE application this is the library for you.

The goal is to make it even easier to use Nats in you're Java EE application. It uses the official jnats artifact from
nats.io.

To achieve the goal we're relying on jakarta Interceptors and a few custom annotations. We aim to make publishing and
subscribing a breeze by annotating your beans with `@Publish(subject='mysubject')` or `@Subscribe(subject='subject')`.

We're strong believers of power to the user and try to minimize our own abstractions and keeping all
customization/configuration for jnats available.

## Connect to Nats

When this library is on the classpath it adds a NatsConnectionProducer that produces an `io.nats.client.Connection`. Is
has zero configuration and thus will connect to localhost on the default port.
You can customize the connection adding a bean of type `io.nats.client.Options`. If this bean is found, it will use it
to create the connection. Allowing you to customize everything that is allowed by jnats.

## Publish messages

To publish messages annotate any bean class or method with `@Publish(subject = "mysubject")`. An interceptor will pick
it up, and use the `Connection` produced by this library. I.E.:

```java
import jakarta.enterprise.context.ApplicationScoped;
import nl.robinthedev.nats.annotation.Publish;

import java.io.Serializable;

@ApplicationScoped
@Publish(subject = "myrootsubject") //Optional annotation, subjects will be joined with a '.'
class MyPublisher {

  @Publish(subject = "mysubject")
  Serializable sendTo() {
    return "the payload body to publish to the subject from the annotation";
  }

  Serializable doNotPublish() {
    return "Method is not annotated so it won't publish, regardless of class annotation.";
  }
}
```

If the `sendTo()` method is invoked it will publish the payload to subject `myrootsubject.mysubject`. Only methods annotated with `@Publish` will be processed. 

Two rules when a method has been annotated with `@Publish`: 
- At least one subject must be defined, either on the class or the method.
- The method must have a return type. We'll try to serialize it even it's not of type `Serializable`, it can't be void.

We will greet you with a `IllegalStateException` at runtime if these rules get broken.
 