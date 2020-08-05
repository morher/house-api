# House API
House is a framework for home automation. It consist of three parts: the API, house modules and the container. The modules consists of services that are run by the container. The services communicate between themselves through events. The API defines the interfaces module services uses to communicate and operate.

## Modules
A module is one or more services packed in a jar, with all dependencies except the House API package.

## Services
A service in House is a class that implements HouseService, although implementations should rather extend the BaseService class to be future-proof in case of new additions to the interface. Services should be made to serve a small dedicated purpose with a general event interface. The services should work together to provide more advanced functionality.

As an example a doorbell-service would only be responsible to receive button-click events and send doorbell-events. It could include functionality to block repeated clicks, but interfacing with the actual button should be outsources to a separate service. On a Raspberry PI there could be a service responsible for communicating with the physical button through the GPIO pins. The GPIO service would send button push and button release events that would be picked up by the doorbell service. The doorbell service could send events for turning on and off the chime that the GPIO service would pick up and activate another pin accordingly. Although, not connecting these services directly, but rather through general events, one could replace the chime with a service sending a notification to Slack.