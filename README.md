# File README del progetto Tanks2D

### INFORMAZIONI
- Progetto base: https://github.com/TommasoMardegan/tommasomardegan.github.io/tree/main/tankBattle2D
### FUNZIONALITA' DEL GIOCO
- Movimento dei carri armati visualizzato in contemporanea sui client
- Sparo dei carri armati visualizzato in contemporanea sui client
- Blocchi non distruggibili che fanno da ostacoli agli spari
- Diminuzione delle vite del client in seguito a quando viene colpito
- Schermate di vittoria/sconfitta
### PER GIOCARE
- attualmente nella versione di prova, si hanno una cartella clientProva e client
- ognuna ha la propria socket che si connette al server (è come giocare su due host in remoto)
- aprire in una finestra il server, in una il client e nell'altra threadClient
- divertiti!
O in alternativa:
- modificare nella classe "Messaggio" del server e dei client gli indirizzi IP e le porte per porter connettersi a un host
  che fa da server e giocsare
