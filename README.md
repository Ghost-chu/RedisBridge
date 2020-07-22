#RedisBridge
A cross-platform real-time data transfer library.

## 

## How 2 use

### Bukkit/Spigot/Paper
````java
class YourPlugin{
    private final YourListener yourMessageListener = new YourListener();
    public void onEnable(){
        Plugin plugin = Bukkit.getPluginManager().getPlugin("RedisBridge");
        RedisBridge bridge;
        if(plugin != null && plugin.isEnabled()){
            bridge = (RedisBridge)plugin;
            bridge.register("MineRestaurant", yourMessageListener);
            /*
            * namespace = A unique string to prevent conflict with others plugin
            * channel = The channel name to help your plugin distinguish message type (for example: The fruit is Apple, Banana or Orange)
            * data = The text data to transfer, If you want transfer binary data, try Base64 or Gson to help you Serialize and Deserialize.           
            */
            bridge.push("MineRestaurant", "order", "Apple x5 please.");
            bridge.push("MineRestaurant", "pay", "Mary pay cashier 5 dollars.");
        }else{
            System.out.println("Please install RedisBridge on your server first!");
        }
    }
}
class YourListener implements com.mcsunnyside.redisbridge.common.bus.Listener{
    @Subscribe(channel = "order")
    public void onMessage(@NotNull String data){
         System.out.println("Customer order: " + data);
    }   
    @Subscribe(channel = "pay")
    public void onMessage(@NotNull String data){
         System.out.println("Customer pay: " + data);
    }  
}
````

### Bungeecord
````java
class YourPlugin{
    private final YourListener yourMessageListener = new YourListener();
    public void onEnable(){
        Plugin plugin = getProxy().getPluginManager().getPlugin("RedisBridge");
        RedisBridge bridge;
        if(plugin != null && plugin.isEnabled()){
            bridge = (RedisBridge)plugin;
            bridge.register("MineRestaurant", yourMessageListener);
            /*
            * namespace = A unique string to prevent conflict with others plugin
            * channel = The channel name to help your plugin distinguish message type (for example: The fruit is Apple, Banana or Orange)
            * data = The text data to transfer, If you want transfer binary data, try Base64 or Gson to help you Serialize and Deserialize.           
            */
            bridge.push("MineRestaurant", "order", "Apple x5 please.");
            bridge.push("MineRestaurant", "pay", "Mary pay cashier 5 dollars.");
        }else{
            System.out.println("Please install RedisBridge on your server first!");
        }
    }
}
class YourListener implements com.mcsunnyside.redisbridge.common.bus.Listener{
    @Subscribe(channel = "order")
    public void onMessage(@NotNull String data){
         System.out.println("Customer order: " + data);
    }   
    @Subscribe(channel = "pay")
    public void onMessage(@NotNull String data){
         System.out.println("Customer pay: " + data);
    }  
}
````