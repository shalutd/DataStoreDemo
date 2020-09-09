# DataStore - Android
Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. 
DataStore uses Kotlin's coroutines and Flow to store data asynchronously, consistently, and transactionally. It will replace SharedPreferences. 
## Two Types of DataStore:
		
1. Preferences DataStore stores and accesses data using keys. This implementation does not require a predefined schema, and it does not provide type safety.
2. Proto DataStore stores data as instances of a custom data type. This implementation requires you to define a schema using protocol buffers, but it provides 
   type safety.
