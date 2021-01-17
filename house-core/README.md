# House API - Core
The core package contains a set of utilities with the purpose of streamlining module creation.

## Configuration mapping
The ConfigMapper can take a ConfigurationElement and turn it into a custom configuration class.

## Event templates
The event distribution system will distribute any event of any type. But if the different services does not know how to deal with the events sent by the other services, creating automation is much harder. To address that, the core package contains a set of common event types and interactions. The event templates are defined through annotated interfaces, where each method represents on event type in a logical set. If a module needs to deal with other event types than what is already defined in the core package, a seperate artifact with event templates could be provided and shared among the involved modules.

Included Event templates:
| Event template set | Type | Use |
| ------------------ | ----------- | --- |
| _Diagnose_ |
| net.morher.house.core.event.diag.Heartbeat | React | Sent periodically by the node to show sign of life. |
| net.morher.house.core.event.diag.Ping | Command / React |
| _Buttons_ |
| net.morher.house.core.event.button.ButtonCommand  | Command | Control a button-like interface, like a remote. |
| net.morher.house.core.event.button.ButtonListener | React   | React to actions on a button-like interface, like a physical button hooked up to a GPIO pin or a touch on a touchscreen. |
| net.morher.house.core.event.button.SwitchListener | React   | React to actions on a multi-position switch-like interface, like a physical button hooked up to a GPIO pin or a touch on a touchscreen. |
| _Lights_ |
| net.morher.house.core.event.lights.LightCommand | Command | Control lights, including turning lights on and off, as well as dimming where supported. |
| net.morher.house.core.event.lights.LightListener | React | React to changes to a light/lamp as a result of a LightCommand event, a local control change or a timed change. |

To bring life to the interfaces, the core package also includes an adapter and event listener factory. The adapter implements the interface. Modules that are sending events, can call method directly on the adapter and it will create the corresponding events and dispatch them. Likewise, modules that receive events can create a delegate object implementing the interface and register it with an event listener. When a matching event is received by the event listener, it will call the corresponding method on the delegate.
