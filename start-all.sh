command &>/dev/null &



# starte torerkennung
sh /home/pi/git-repos/torerkennung/run.sh &>/dev/null &
# starte server
sh /home/pi/git-repos/Tischkicker/start-server.sh &>/dev/null &
# ein paar Sekunden warten, bis server da ist.
sleep 10s
# Client (GUI) starten
sh /home/pi/git-repos/Tischkicker/start-client.sh &>/dev/null &