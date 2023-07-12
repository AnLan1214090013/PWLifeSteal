//package cn.pixelwar.pwlifesteal.PlayerStats;
//
//
//import org.bukkit.Location;
//
//public class HomeStat {
//
//    private String name;
//    private Location location;
//
//    public HomeStat(String name, Location location) {
//        this.name = name;
//        this.location = location;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//
//    public String getStringLocation(){
//        if (this.location==null){
//            return null;
//        }
//        return this.location.getWorld().getName()+";"+
//                this.location.getX()+";"+
//                this.location.getY()+";"+
//                this.location.getZ()+";"+
//                this.location.getYaw()+";"+
//                this.location.getPitch();
//    }
//}
