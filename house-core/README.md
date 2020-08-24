# House API - Core
The core package contains a set of utilities with the purpose of streamlining module creation.

## Configuration mapping
The ConfigMapper can take a ConfigurationElement and turn it into a custom configuration class.

## Event templates
The event distribution system will distribute any event of any type. But if the different services does not know how to deal with the events sent by the other services, creating automation is much harder. To address that, the core package contains a set of common event types and interactions. The event templates are defined through annotated interfaces, where each method represents on event type in a logical set. If a module needs to deal with other event types than what is already defined in the core package, a seperate artifact with event templates could be provided and shared among the involved modules.

To bring life to the interfaces, the core package also includes an adapter and event listener factory. The adapter implements the interface. Modules that are sending events, can call method directly on the adapter and it will create the corresponding events and dispatch them. Likewise, modules that receive events can create a delegate object implementing the interface and register it with an event listener. When a matching event is received by the event listener, it will call the corresponding method on the delegate.