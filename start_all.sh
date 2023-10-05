cd /home/pi/git-repos/torerkennung
# mvn clean package

# starte torerkennung
cd /home/pi/git-repos/torerkennung/target/distribution/
sudo java -Dremote=true -Dbcm-sensor-1=24 -Dbcm-sensor-2=23 -cp pi4j-core-2.3.0.jar:pi4j-library-pigpio-2.3.0.jar:pi4j-plugin-pigpio-2.3.0.jar:pi4j-plugin-raspberrypi-2.3.0.jar:slf4j-api-1.7.32.jar:slf4j-simple-1.7.32.jar:pi4j-example-minimal-0.0.1.jar:torerkennung-0.0.1.jar de.shgruppe.torerkennung.Main &

sleep 2s
 
java -jar /home/pi/git-repos/Tischkicker/Server/target/Server-1.0.0-SNAPSHOT.jar &

sleep 20s

cd /home/pi/git-repos/bbtest-projekt/
sudo java -Dremote=true -Dbutton-addresses=16,13,20,19,21,26 -Dbutton-one-text=😃😄😎😋😉️⚡😸😹😻😗😘☃Text -jar ./target/pi4j-example-fatjar.jar &

sleep 5s

java -jar /home/pi/git-repos/Tischkicker/Client/target/Client-1.0.0-SNAPSHOT-jar-with-dependencies.jar &