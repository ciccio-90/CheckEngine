;/////////////////////////////////////////////
;///	Variabili globali e funzioni	  ///
;///////////////////////////////////////////

(defglobal ?*preparazione-utente* = "")

(defglobal ?*domanda* = "")

(defglobal ?*soluzione* = "")

(defglobal ?*spiegazione* = "")

(defglobal ?*aiuto* = "")

(defglobal ?*glossario* = "")

(deffunction domanda-random ()

	(round (mod (time) 5))

)

;////////////////////////////
;///	Profilo utente	 ///
;//////////////////////////

(defrule avvia-sistema 
	(initial-fact) 
=> 
	(assert (livello base))
	(bind ?r (domanda-random))
	(assert (#-domanda-lb ?r))
)	

(defrule ritratta-livello-base
	?d <- (#-domanda-lb 5) 
=> 
	(retract ?d)
	(bind ?r (domanda-random))
	(assert (#-domanda-lb ?r))
)		

(defrule prima-domanda-livello-base
	?f <- (livello base)
	(#-domanda-lb 0) 
=> 
	(bind ?*domanda* "Hai mai lavorato in un'autofficina meccanica?")
	(retract ?f)
)

(defrule seconda-domanda-livello-base
	?f <- (livello base)
	(#-domanda-lb 1) 
=> 
	(bind ?*domanda* "Sei un meccanico?")
	(retract ?f)
)

(defrule terza-domanda-livello-base
	?f <- (livello base)
	(#-domanda-lb 2) 
=> 
	(bind ?*domanda* "Hai mai effettuato una riparazione ad un veicolo?")
	(retract ?f)
)

(defrule quarta-domanda-livello-base
	?f <- (livello base)
	(#-domanda-lb 3) 
=> 
	(bind ?*domanda* "Hai mai sostituito un pneumatico?")
	(retract ?f)
)

(defrule quinta-domanda-livello-base
	?f <- (livello base)
	(#-domanda-lb 4) 
=> 
	(bind ?*domanda* "Hai mai sostituito una lampada ad un faro di un veicolo?")
	(retract ?f)
)

(defrule utente-incompetente
	(livello-base non superato)
=>
	(assert (preparazione-utente insufficiente))
	(bind ?*preparazione-utente* "insufficiente")
)

(defrule non-utente-incompetente
	(livello-base superato)
=>
	(assert (livello intermedio))
	(bind ?r (domanda-random))
	(assert (#-domanda-li ?r))
)	

(defrule ritratta-livello-intermedio
	?d <- (#-domanda-li 5) 
=> 
	(retract ?d)
	(bind ?r (domanda-random))
	(assert (#-domanda-li ?r))
)

(defrule prima-domanda-livello-intermedio
	?f <- (livello intermedio)
	(#-domanda-li 0) 
=> 
	(bind ?*domanda* "Hai mai sostituito l'olio motore?")
	(retract ?f)
)

(defrule seconda-domanda-livello-intermedio
	?f <- (livello intermedio)
	(#-domanda-li 1) 
=> 
	(bind ?*domanda* "Hai mai sostituito un fusibile?")
	(retract ?f)
)

(defrule terza-domanda-livello-intermedio
	?f <- (livello intermedio)
	(#-domanda-li 2) 
=> 
	(bind ?*domanda* "Hai mai sostituito i pattini dei freni?")
	(retract ?f)
)

(defrule quarta-domanda-livello-intermedio
	?f <- (livello intermedio)
	(#-domanda-li 3) 
=> 
	(bind ?*domanda* "Hai mai sostituito la cinghia accessori?")
	(retract ?f)
)

(defrule quinta-domanda-livello-intermedio
	?f <- (livello intermedio)
	(#-domanda-li 4) 
=> 
	(bind ?*domanda* "Hai mai sostituito la batteria?")
	(retract ?f)
)

(defrule non-utente-semicompetente
	(livello-intermedio non superato)
=>
	(assert (preparazione-utente insufficiente))
	(bind ?*preparazione-utente* "insufficiente")
)

(defrule utente-semicompetente
	(livello-intermedio superato)
=>
	(assert (livello alto))
	(bind ?r (domanda-random))
	(assert (#-domanda-la ?r))
)	

(defrule ritratta-livello-alto
	?d <- (#-domanda-la 5) 
=> 
	(retract ?d)
	(bind ?r (domanda-random))
	(assert (#-domanda-la ?r))
)

(defrule prima-domanda-livello-alto
	?f <- (livello alto)
	(#-domanda-la 0) 
=> 
	(bind ?*domanda* "Hai mai sostituito il kit distribuzione?")
	(retract ?f)
)

(defrule seconda-domanda-livello-alto
	?f <- (livello alto)
	(#-domanda-la 1) 
=> 
	(bind ?*domanda* "Hai mai sostituito un iniettore?")
	(retract ?f)
)

(defrule terza-domanda-livello-alto
	?f <- (livello alto)
	(#-domanda-la 2) 
=> 
	(bind ?*domanda* "Hai mai sostituito la pompa di alta pressione?")
	(retract ?f)
)

(defrule quarta-domanda-livello-alto
	?f <- (livello alto)
	(#-domanda-la 3) 
=> 
	(bind ?*domanda* "Hai mai sostituito l'alternatore?")
	(retract ?f)
)

(defrule quinta-domanda-livello-alto
	?f <- (livello alto)
	(#-domanda-la 4) 
=> 
	(bind ?*domanda* "Hai mai sostituito il motorino di avviamento?")
	(retract ?f)
)

(defrule non-utente-competente
	(livello-alto non superato)
=>
	(assert (preparazione-utente sufficiente))
	(bind ?*preparazione-utente* "sufficiente")
)

(defrule utente-competente
	(livello-alto superato)
=>
	(assert (preparazione-utente discreta))
	(bind ?*preparazione-utente* "discreta")
)	

;//////////////////////////
;///	Diagnosi	   ///
;////////////////////////

(defrule avvio-diagnosi
	(preparazione-utente ?)
=>
	(bind ?*domanda* "Il veicolo si avvia?")
)

(defrule avvio-veicolo
	(preparazione-utente ?)
	(veicolo parte)
=>
	(bind ?*domanda* "La spia dell'olio motore e' accesa?")
	(bind ?*glossario* "Spia dell'olio: dispositivo di segnalazione luminoso volto ad avvisare il conducente del veicolo di una condizione non ottimale riguardante l'olio motore.")
)

(defrule spia-olio-spenta
	(preparazione-utente ?)
	(veicolo parte)
	(spia olio spenta)
=>
	(bind ?*domanda* "La spia dell'alternatore e' accesa?")
	(bind ?*glossario* "Spia dell'alternatore: dispositivo di segnalazione luminoso volto ad avvisare il conducente del veicolo che l'alternatore non ricarica la batteria.")
)

(defrule spia-alternatore-accesa
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio spenta)
	(spia alternatore accesa)
=>
	(bind ?*domanda* "La temperatura dell'acqua motore e' salita oltre 120 �C?")
	(bind ?*spiegazione* "Potrebbe essersi spezzata la cinghia accessori.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule spia-alternatore-accesa-incompetente
	(preparazione-utente insufficiente)
	(veicolo parte)
	(spia olio spenta)
	(spia alternatore accesa)
=>
	(bind ?*soluzione* "Recati il prima possibile dalla tua autofficina meccanica di fiducia!")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule temperatura-acqua-elevata
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio spenta)
	(spia alternatore accesa)
	(temperatura acqua elevata)
=>
	(bind ?*soluzione* "Sostituisci la cinghia accessori!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' spentama quella dell'alternatore e' accesa e che la temperatura dell'acqua e' elevata, il mio consiglio e' quello di provare a sostituire la cinghia accessori.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Metti il veicolo sul ponte elevatore.3) Rimuovi la ruota anteriore destra.4) Rimuovi il supporto motore destro.5) Rimuovi la cinghia accessori sostituendola con la nuova e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Cinghia accessori: organo di trasmissione meccanica utilizzato per collegare in modo leggermente elastico, ma comunque solidale, piu dispositivi meccanici, mediante l'uso di pulegge, il cui interasse e' piuttosto elevato.")
)

(defrule temperatura-acqua-normale
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio spenta)
	(spia alternatore accesa)
	(temperatura acqua normale)
=>
	(bind ?*soluzione* "Sostituisci l'alternatore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' spentama quella dell'alternatore e' accesa e che la temperatura dell'acqua e' normale, il mio consiglio e' quello di provare a sostituire l'alternatore.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Metti il veicolo sul ponte elevatore.3) Rimuovi la ruota anteriore destra.4) Rimuovi il supporto motore destro.5) Rimuovi la cinghia accessori.6) Scollega le connessioni elettroniche.7) Smonta l'alternatore togliendo i bulloni che lo fissano al monoblocco.8) Installa il nuovo alternatore e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Alternatore: macchina elettrica rotante basata sul fenomeno dell'induzione elettromagnetica, che trasforma energia meccanica in energia elettrica sotto forma di corrente alternata assumendo la funzione di trasduttore.")
)

(defrule spia-alternatore-spenta
	(preparazione-utente ?)
	(veicolo parte)
	(spia olio spenta)
	(spia alternatore spenta)
=>
	(bind ?*soluzione* "Non e' stato rilevato alcun guasto!")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule spia-olio-accesa
	(preparazione-utente ?)
	(veicolo parte)
	(spia olio accesa)
=>
	(bind ?*domanda* "Il livello dell'olio motore e' buono?")
	(bind ?*spiegazione* "Se il livello dell'olio motore e' basso, inevitabilmente si accende la relativa spia.")
	(bind ?*aiuto* "1) Spegni il motore ed apri il cofano.2) Estrai l'astina e puliscila con un panno pulito.3) Reinserisci l'astina nel suo alloggiamento e rimuovila nuovamente.4) Controlla il livello del lubrificante, che deve essere compreso fra le due tacche.")
	(bind ?*glossario* "Olio motore: miscela liquida utilizzata per la lubrificazione degli organi meccanici del motore.")
)

(defrule livello-olio-basso
	(preparazione-utente ?)
	(veicolo parte)
	(spia olio accesa)
	(livello olio basso)
=>
	(bind ?*soluzione* "Rabbocca l'olio motore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' accesa e che il livello dello stesso e' basso, il mio consiglio e' quello di provare ad effettuare il rabbocco dell'olio motore.")
	(bind ?*aiuto* "1) Spegni il motore ed apri il cofano.2) Individua il tappo sul coperchio della testata contrassegnato dallo stesso simbolo della spia dell'olio sul cruscotto.3) Aggiungi gradualmente la quantita' di lubrificante della giusta gradazione necessaria a innalzare il livello entro l'intervallo indicato dalle tacche sull'astina, facendo molta attenzione a non superare mai il limite di riempimento.")
	(bind ?*glossario* "Olio motore: miscela liquida utilizzata per la lubrificazione degli organi meccanici del motore.")
)

(defrule livello-olio-buono-incompetente
	(preparazione-utente insufficiente)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
=>
	(bind ?*soluzione* "Recati il prima possibile dalla tua autofficina meccanica di fiducia!")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule livello-olio-buono
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
=>
	(bind ?*domanda* "Controlla la tensione sotto il termocontatto dell'olio motore. Il valore misurato e' circa 12 volt?")
	(bind ?*spiegazione* "Potrebbe esserci un guasto al termocontatto dell'olio motore.")
	(bind ?*aiuto* "1) Spegni il motore ed apri il cofano.2) Scollega il connettore del termocontatto dell'olio motore.3) Collega il multimetro al connettore del termocontatto dell'olio motore.4) Avvia il motore e leggi il voltaggio sul monitor.")
	(bind ?*glossario* "Termocontatto dell'olio motore: dispositivo di segnalazione volto ad avvisare di una condizione non ottimale della pressione dell'olio motore.")
)

(defrule tensione-termocontatto-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
	(tensione termocontatto buona)
=>
	(bind ?*domanda* "Controlla la pressione dell'olio motore. Il valore misurato e' compreso tra 0.2 e 0.4 bar?")
	(bind ?*spiegazione* "Potrebbe esserci un guasto alla pompa dell'olio motore.")
	(bind ?*aiuto* "1) Spegni il motore ed apri il cofano.2) Rimuovi il termocontatto dell'olio motore.3) Collega il manometro all'alloggiamento del termocontatto dell'olio motore.4) Avvia il motore e leggi la pressione sul monitor.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule pressione-olio-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
	(tensione termocontatto buona)
	(pressione olio buona)
=>
	(bind ?*soluzione* "Sostituisci il termocontatto dell'olio motore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' accesa, che il livello e la pressione dello stesso sono buoni e che arriva corrente sotto il suo termocontatto, il mio consiglio e' quello di provare a sostituire il termocontatto dell'olio motore.")
	(bind ?*aiuto* "1) Spegni il motore ed apri il cofano.2) Scollega il connettore del termocontatto dell'olio motore.3) Rimuovi il vecchio termocontatto dell'olio motore.4) Installa il nuovo termocontatto dell'olio motore e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Termocontatto dell'olio motore: dispositivo di segnalazione volto ad avvisare di una condizione non ottimale della pressione dell'olio motore.")
)

(defrule pressione-olio-bassa
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
	(tensione termocontatto buona)
	(pressione olio bassa)
=>
	(bind ?*soluzione* "Sostituisci la pompa dell'olio motore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' accesa, che il livello dello stesso e' buono ma non lo e' la pressione e che arriva corrente sotto il suo termocontatto, il mio consiglio e' quello di provare a sostituire la pompa dell'olio motore.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Metti il veicolo sul ponte elevatore.3) Smonta la coppa dell'olio rimuovendo i bulloni intorno alla stessa.4) Smonta la cipolla di pescaggio.5) Rimuovi la ruota anteriore destra.6) Rimuovi il supporto motore destro.7) Rimuovi la cinghia accessori e la puleggia dell'albero motore.8) Rimuovi il coperchio posto sull'asse a camme.9) Metti in fase il motore inserendo una spina da 8 mm nell'albero motore e un'altra nell'asse a camme.10) Smonta la pompa dell'olio motore togliendo i bulloni che la fissano al monoblocco.11) Installa la nuova pompa dell'olio motore e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Pompa dell'olio motore: organo che mette in circolo l'olio motore.")
)

(defrule tensione-termocontatto-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
	(tensione termocontatto non buona)
=>
	(bind ?*domanda* "Il fusibile del termocontatto dell'olio motore e' bruciato?")
	(bind ?*spiegazione* "Potrebbe esserci un cortocircuito al filo conduttore del termocontatto dell'olio motore.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il coperchio posto sulla scatola dei fusibili.3) Scollega il cablaggio elettrico.4) Rimuovi il fusibile del termocontatto dell'olio motore.5) Collega il multimetro al fusibile.6) Se il multimetro segna un numero minore di 1, allora il fusibile e' funzionante.")
	(bind ?*glossario* "Fusibile: dispositivo elettrico in grado di proteggere un circuito dalle sovracorrenti causate per esempio dai cortocircuiti. Termocontatto dell'olio motore: dispositivo di segnalazione volto ad avvisare di una condizione non ottimale della pressione dell'olio motore.")
)

(defrule fusibile-termocontatto-buono
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
	(tensione termocontatto non buona)
	(fusibile termocontatto buono)
=>
	(bind ?*soluzione* "Sostituisci il filo conduttore del termocontatto dell'olio motore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' accesa, che il livello dello stesso e' buono, che non arriva corrente sotto il suo termocontatto e che il fusibile relativo a quest'ultimo e' in buone condizioni, il mio consiglio e' quello di provare a sostituire il filo conduttore del termocontatto dell'olio motore.")
	(bind ?*aiuto* "1) Spegni il motore ed apri il cofano.2) Scollega il connettore del termocontatto dell'olio motore.3) Rimuovi il filo conduttore del termocontatto dell'olio motore.4) Installa il nuovo filo conduttore del termocontatto dell'olio motore e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Filo conduttore: materiale in grado di far scorrere corrente in modo veloce al suo interno. Termocontatto dell'olio motore: dispositivo di segnalazione volto ad avvisare di una condizione non ottimale della pressione dell'olio motore.")
)

(defrule fusibile-termocontatto-fuso
	(preparazione-utente sufficiente | discreta)
	(veicolo parte)
	(spia olio accesa)
	(livello olio buono)
	(tensione termocontatto non buona)
	(fusibile termocontatto fuso)
=>
	(bind ?*soluzione* "Sostituisci il fusibile del termocontatto dell'olio motore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel quadro la spia dell'olio motore e' accesa, che il livello dello stesso e' buono, che non arriva corrente sotto il suo termocontatto e che il fusibile relativo a quest'ultimo e' in brutte condizioni, il mio consiglio e' quello di provare a sostituire il fusibile del termocontatto dell'olio motore.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il coperchio posto sulla scatola dei fusibili.3) Scollega il cablaggio elettrico.4) Rimuovi il fusibile del termocontatto dell'olio motore sostituendolo con uno nuovo e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Fusibile: dispositivo elettrico in grado di proteggere un circuito dalle sovracorrenti causate per esempio dai cortocircuiti. Termocontatto dell'olio motore: dispositivo di segnalazione volto ad avvisare di una condizione non ottimale della pressione dell'olio motore.")
)

(defrule non-avvio-veicolo-incompetente
	(preparazione-utente insufficiente)
	(veicolo non parte)
=>
	(bind ?*soluzione* "Chiama il carroattrezzi e recati dalla tua autofficina meccanica di fiducia!")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule non-avvio-veicolo-semicompetente
	(preparazione-utente sufficiente)
	(veicolo non parte)
=>
	(bind ?*domanda* "E' presente il carburante nel serbatoio?")
	(bind ?*spiegazione* "Potrebbe esserci un guasto al galleggiante e segnalare la falsa presenza di carburante nel serbatoio.")
	(bind ?*aiuto* "1) Scollega la pompa di alta pressione dalla rampa iniettori.2) Avvia il motore e nota se la pompa di alta pressione spruzza fuori il carburante.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)
	
(defrule carburante-assente
	(preparazione-utente sufficiente)
	(veicolo non parte)
	(assenza carburante)
=>
	(bind ?*soluzione* "Aggiungi il carburante!")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule carburante-presente
	(preparazione-utente sufficiente)
	(veicolo non parte)
	(presenza carburante)
=>
	(bind ?*domanda* "Il motore gira?")
	(bind ?*spiegazione* "Potrebbero esserci dei guasti ad alcuni organi elettrici del motore.")
	(bind ?*aiuto* "Prova a mettere in moto il veicolo e nota se il motore cerca di avviarsi.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule non-avvio-veicolo-competente
	(preparazione-utente discreta)
	(veicolo non parte)
=>
	(bind ?*domanda* "Il motore gira?")
	(bind ?*spiegazione* "Potrebbero esserci dei guasti ad alcuni organi elettrici del motore.")
	(bind ?*aiuto* "Prova a mettere in moto il veicolo e nota se il motore cerca di avviarsi.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule motore-non-gira
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
=>
	(bind ?*domanda* "Controlla la tensione della batteria. Il valore misurato e' compreso tra 12.10 e 12.50 volt?")
	(bind ?*spiegazione* "Il voltaggio di una batteria carica e funzionante deve misurare intorno a 12 volt.")
	(bind ?*aiuto* "Collega il multimetro ai poli della batteria e leggi il voltaggio sul monitor.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule motore-gira
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
=>
	(bind ?*domanda* "Controlla il sincronismo tra l'albero motore e l'asse a camme. Il valore misurato sui sensori di giri e fase e' compreso tra 0 e 5 volt?")
	(bind ?*spiegazione* "La misurazione della fasatura, punto di ottimale apertura delle valvole di un motore, e' necessaria per controllare il corretto sincronismo della distribuzione.")
	(bind ?*aiuto* "1) Metti il veicolo sul ponte elevatore.2) Rimuovi la ruota anteriore destra.3) Rimuovi il supporto motore destro.4) Rimuovi la cinghia accessori e la puleggia dell'albero motore.5) Rimuovi il coperchio posto sull'asse a camme.6) Collega il multimetro prima sul sensore di giri posto sull'asse a camme e poi su quello di fase posto sull'albero motore.7) Avvia il motore e leggi il voltaggio sul monitor.")
	(bind ?*glossario* "Albero motore: organo di trasmissione di un moto rotatorio. Asse a camme: organo con parti eccentriche calettate, atto a trasmettere particolari leggi di moto agli elementi che vi sono a contatto. Sensore di fase: sensore collegato all'albero a camme e che da' informazioni sulla posizione di tali camme; queste informazioni servono per iniettare il carburante nei cilindri al momento giusto e nella giusta sequenza. Sensore di giri: sensore che serve a determinare il regime di rotazione del motore leggendolo da una ruota fonica calettata sull'albero motore. Sincronismo: condizione di funzionamento di una macchina a campo rotante, per cui la velocità angolare del rotore ha un valore pari alla velocità angolare del campo rotante.")
)

(defrule tensione-sensori-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
=>
	(bind ?*domanda* "Avvia il motore controllando la pressione del carburante. Il valore misurato e' compreso tra 2 e 2.5 bar?")
	(bind ?*spiegazione* "Potrebbe esserci un guasto alla pompa di alta pressione il quale non permetterebbe a quest'ultima di spingere il carburante nella rampa iniettori.")
	(bind ?*aiuto* "1) Scollega la pompa di alta pressione dalla rampa iniettori.2) Attacca il manometro all'ingresso della pompa di alta pressione.3) Avvia il motore e leggi la misurazione della pressione sul monitor.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule pressione-carburante-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
=>
	(bind ?*domanda* "Avvia il motore controllando la pressione della rampa iniettori. Il valore misurato e' compreso tra 200 e 280 bar?")
	(bind ?*spiegazione* "Potrebbe esserci un foro sulla rampa iniettori il quale provocherebbe un'abbassamento di pressione non permettendo a quest'ultima di spingere il carburante negli iniettori.")
	(bind ?*aiuto* "1) Scollega la rampa iniettori dalla pompa di alta pressione.2) Attacca il manometro all'ingresso della rampa iniettori.3) Avvia il motore e leggi la misurazione della pressione sul monitor.")
	(bind ?*glossario* "Rampa iniettori: serbatoio di forma cilindrica che porta il carburante in pressione agli iniettori.")
)

(defrule pressione-iniettori-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
=>
	(bind ?*domanda* "Controlla la tensione sul regolatore della pompa di alta pressione. Il valore misurato e' circa 12 volt?")
	(bind ?*spiegazione* "Potrebbe esserci un guasto al regolatore della pompa di alta pressione il quale non permetterebbe a quest'ultima di avviarsi.")
	(bind ?*aiuto* "Collega il multimetro al regolatore della pompa di alta pressione, avvia il motore e leggi il voltaggio sul monitor.")
	(bind ?*glossario* "Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori. Regolatore della pompa di alta pressione: ha la funzione di regolare e mantenere la pressione nella rampa iniettori in funzione della condizione di carico del motore.")
)

(defrule tensione-pompa-hp-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
	(tensione pompa-hp ok)
=>
	(bind ?*domanda* "Nel regolatore della pompa di alta pressione ci sono tracce di smeriglio?")
	(bind ?*spiegazione* "La pompa di alta pressione potrebbe essere in stato di usura.")
	(bind ?*aiuto* "Smonta il regolatore presente sulla pompa di alta pressione, aprilo e controlla al suo interno.")
	(bind ?*glossario* "Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori. Regolatore della pompa di alta pressione: ha la funzione di regolare e mantenere la pressione nella rampa iniettori in funzione della condizione di carico del motore. Smeriglio: minerale del gruppo degli spinelli ubiquitario sulla crosta terrestre e di notevole importanza tecnologica come componente dei prodotti siderurgici.")
)

(defrule regolatore-pompa-hp-pulito
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
	(tensione pompa-hp ok)
	(assenza smeriglio)
=>
	(bind ?*soluzione* "Sostituisci il regolatore della pompa di alta pressione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante e' buona nella pompa di alta pressione ma non lo e' nella rampa iniettori, che arriva corrente sufficiente alla pompa di alta pressione e che sono assenti tracce di smeriglio, il mio consiglio e' quello di provare a sostituire il regolatore della pompa di alta pressione.")
	(bind ?*aiuto* "Smonta il regolatore presente sulla pompa di alta pressione e sostituiscilo con uno nuovo.")
	(bind ?*glossario* "Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori. Regolatore della pompa di alta pressione: ha la funzione di regolare e mantenere la pressione nella rampa iniettori in funzione della condizione di carico del motore.")
)

(defrule regolatore-pompa-hp-sporco
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
	(tensione pompa-hp ok)
	(presenza smeriglio)
=>
	(bind ?*soluzione* "Sostituisci la pompa di alta pressione, il suo regolatore, il filtro del carburante e pulisci l'impianto di alimentazione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante e' buona nella pompa di alta pressione ma non lo e' nella rampa iniettori, che arriva corrente sufficiente alla pompa di alta pressione e che sono presenti tracce di smeriglio, il mio consiglio e' quello di provare a sostituire la pompa di alta pressione, il suo regolatore e il filtro del carburante e di pulire l'intero impianto di alimentazione.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Metti il veicolo sul ponte elevatore.3) Rimuovi la ruota anteriore destra.4) Rimuovi il supporto motore destro.5) Rimuovi la cinghia accessori e la puleggia dell'albero motore.6) Rimuovi il coperchio posto sull'asse a camme.7) Metti in fase il motore inserendo una spina da 8 mm nell'albero motore e un'altra nell'asse a camme.8) Smonta la pompa di alta pressione completa del suo regolatore e il filtro del carburante.9) Smonta l'intero impianto di alimentazione e puliscilo soffiandoci dentro con una pistola ad aria compressa.10) Installa i nuovi pezzi di ricambio e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Filtro del carburante: filtro volto allo scarico periodico di acqua o impurita' che possono provocare danni per corrosione e usura ai componenti della pompa di alta pressione e agli iniettori. Impianto di alimentazione: parte del motore che serve per l'introduzione di carburante, aria e olio nei cilindri. Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori. Regolatore della pompa di alta pressione: ha la funzione di regolare emantenere la pressione nella rampa iniettori in funzione della condizione di carico del motore.")
)

(defrule tensione-pompa-hp-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
	(tensione pompa-hp ko)
=>
	(bind ?*domanda* "Il cablaggio della pompa di alta pressione e' in buono stato?")
	(bind ?*spiegazione* "Potrebbe esserci qualche filo elettrico, in stato di usura, della pompa di alta pressione che non permetterebbe di farle arrivare corrente sufficiente per il suo avvio.")
	(bind ?*aiuto* "Controlla con il multimetro ogni singolo filo elettrico del cablaggio della pompa di alta pressione.")
	(bind ?*glossario* "Cablaggio: insieme dei collegamenti e impianti fisici quali cavi, connettori, permutatori. Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori.")
)

(defrule cablaggio-pompa-hp-buono
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
	(tensione pompa-hp ko)
	(cablaggio pompa-hp ok)
=>
	(bind ?*soluzione* "Sostituisci il fusibile della pompa di alta pressione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante e' buona nella pompa di alta pressione ma non lo e' nella rampa iniettori e che non arriva corrente sufficiente alla pompa di alta pressione pur essendo in buono stato il cablaggio elettrico, il mio consiglio e' quello di provare a sostituire il fusibile della pompa di alta pressione.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il coperchio posto sulla scatola dei fusibili.3) Scollega il cablaggio elettrico.4) Rimuovi il fusibile della pompa di alta pressione sostituendolo con uno nuovo e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Fusibile: dispositivo elettrico in grado di proteggere un circuito dalle sovracorrenti causate per esempio dai cortocircuiti. Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori.")
)

(defrule cablaggio-pompa-hp-non-buono
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ko)
	(tensione pompa-hp ko)
	(cablaggio pompa-hp ko)
=>
	(bind ?*soluzione* "Sostituisci il cablaggio della pompa di alta pressione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante e' buona nella pompa di alta pressione ma non lo e' nella rampa iniettori, che non arriva corrente sufficiente alla pompa di alta pressione e che il suo cablaggio elettrico e' in cattivo stato, il mio consiglio e' quello di provare a sostituire il cablaggio della pompa di alta pressione.")
	(bind ?*aiuto* "1) Stacca la batteria, scollega il cablaggio della pompa di alta pressione e sostituiscilo con uno nuovo.")
	(bind ?*glossario* "Cablaggio: insieme dei collegamenti e impianti fisici quali cavi, connettori, permutatori. Pompa di alta pressione: organo che serve a comprimere il combustibile a pressione elevata ed inviarlo pressurizzato nella rampa iniettori.")
)

(defrule pressione-iniettori-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ok)
=>
	(bind ?*domanda* "Controlla gli iniettori. Perdono tutti del carburante?")
	(bind ?*spiegazione* "Potrebbero esserci perdite da alcuni o da addirittura tutti gli iniettori che non permetterebbero al veicolo di avviarsi.")
	(bind ?*aiuto* "1) Rimuovi le graffe che fissano i connettori dei tubi del carburante.2) Chiudi o blocca le estremita' dei tubi per evitare perdite accidentali.3) Prendi il kit per il test del flusso di carburante per gli iniettori.4) Fissa i connettori del kit alle estremita' dei tubi.5) Avvia il motore.6) Osserva le bottiglie e ferma il motore quando sono piene al 75%.7) Gli iniettori fissati a bottiglie che presentano piu' del 10% di punti di carburante extra potrebbero avere un problema di perdita.")
	(bind ?*glossario* "Iniettori: componenti che hanno il compito di immettere il carburante in un modo diverso dal semplice travaso.")
)

(defrule ritorno-carburante-iniettori-uguale
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ok)
	(ritorno carburante uguale)
=>
	(bind ?*soluzione* "Sostituisci tutti gli iniettori!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante e' buona sia nella pompa di alta pressione che nella rampa iniettori e che tutti gli iniettori presentano piu' del 10% di punti di carburante extra, il mio consiglio e' quello di provare a sostituire tutti gli iniettori.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il connettore elettrico e il tubo di aspirazione del carburante collegato al collettore di alimentazione.3) Allenta i bulloni di fissaggio con una chiave a tubo.4) Rimuovi gli iniettori completi di guarnizioni sostituendoli con i nuovi e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Iniettori: componenti che hanno il compito di immettere il carburante in un modo diverso dal semplice travaso.")
)

(defrule ritorno-carburante-iniettori-diverso
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ok)
	(pressione iniettori ok)
	(ritorno carburante diverso)
=>
	(bind ?*soluzione* "Sostituisci solo gli iniettori che perdono del carburante!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante e' buona sia nella pompa di alta pressione che nella rampa iniettori e che non tutti gli iniettori presentano piu' del 10% di punti di carburante extra, il mio consiglio e' quello di provare a sostituire solo gli iniettori che hanno un problema di perdita.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il connettore elettrico e il tubo di aspirazione del carburante collegato al collettore di alimentazione.3) Allenta i bulloni di fissaggio con una chiave a tubo.4) Rimuovi gli iniettori completi di guarnizioni sostituendoli con i nuovi e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Iniettori: componenti che hanno il compito di immettere il carburante in un modo diverso dal semplice travaso.")
)

(defrule pressione-carburante-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ko)
=>
	(bind ?*domanda* "Controlla la tensione in arrivo sulla pompa di bassa pressione. Il valore misurato e' circa 12 volt?")
	(bind ?*spiegazione* "Potrebbe esserci un guasto alla pompa di bassa pressione il quale non permetterebbe a quest'ultima di avviarsi.")
	(bind ?*aiuto* "1) Apri le portiere posteriori.2) Smonta e rimuovi i sedili posteriori.3) Rimuovi il coperchio per poter accedere alla pompa di bassa pressione.4) Collega il multimetro al cablaggio elettrico della pompa di bassa pressione.5) Avvia il motore e leggi il voltaggio sul monitor del multimetro.")
	(bind ?*glossario* "Pompa di bassa pressione: componente che ha il compito di inviare il carburante dal serbatoio del veicolo alla pompa di alta pressione.")
)

(defrule tensione-pompa-lp-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ko)
	(tensione pompa-lp ok)
=>
	(bind ?*soluzione* "Sostituisci la pompa di bassa pressione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante non e' buona e che arriva corrente sufficiente al cablaggio elettrico della pompa di bassa pressione per il suo avvio, il mio consiglio e' quello di provare a sostituire la pompa di bassa pressione.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Apri le portiere posteriori.3) Smonta e rimuovi i sedili posteriori.4) Rimuovi il coperchio per poter accedere alla pompa di bassa pressione.5) Scollega il cablaggio del carburante e quello elettrico dalla pompa di bassa pressione.6) Rimuovi la pompa di bassa pressione sostituendola con una nuova e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Pompa di bassa pressione: componente che ha il compito di inviare il carburante dal serbatoio del veicolo alla pompa di alta pressione.")
)

(defrule tensione-pompa-lp-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ko)
	(tensione pompa-lp ko)
=>
	(bind ?*domanda* "Il cablaggio della pompa di bassa pressione e' in buono stato?")
	(bind ?*spiegazione* "Potrebbe esserci qualche filo elettrico, in stato di usura, della pompa di bassa pressione che non permetterebbe di farle arrivare corrente sufficiente per il suo avvio.")
	(bind ?*aiuto* "1) Apri le portiere posteriori.2) Smonta e rimuovi i sedili posteriori.3) Rimuovi il coperchio per poter accedere alla pompa di bassa pressione.4) Controlla con il multimetro ogni singolo filo elettrico del cablaggio della pompa di bassa pressione.")
	(bind ?*glossario* "Cablaggio: insieme dei collegamenti e impianti fisici quali cavi, connettori, permutatori. Pompa di bassa pressione: componente che ha il compito di inviare il carburante dal serbatoio del veicolo alla pompa di alta pressione.")
)

(defrule cablaggio-pompa-lp-non-buono
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ko)
	(tensione pompa-lp ko)
	(cablaggio pompa-lp ko)
=>
	(bind ?*soluzione* "Sostituisci il cablaggio della pompa di bassa pressione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante non e' buona, che non arriva corrente sufficiente alla pompa di bassa pressione per il suo avvio e che il suo cablaggio elettrico e' in cattivo stato, il mio consiglio e' quello di provare a sostituire il cablaggio elettrico della pompa di bassa pressione.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Apri le portiere posteriori.3) Smonta e rimuovi i sedili posteriori.4) Rimuovi il coperchio per poter accedere alla pompa di bassa pressione.5) Scollega il cablaggio elettrico dalla pompa di bassa pressione sostituendolo con uno nuovo e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Cablaggio: insieme dei collegamenti e impianti fisici quali cavi, connettori, permutatori. Pompa di bassa pressione: componente che ha il compito di inviare il carburante dal serbatoio del veicolo alla pompa di alta pressione.")
)

(defrule cablaggio-pompa-lp-buono
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ok)
	(pressione carburante ko)
	(tensione pompa-lp ko)
	(cablaggio pompa-lp ok)
=>
	(bind ?*soluzione* "Sostituisci il fusibile della pompa di bassa pressione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase funzionano adeguatamente, che la pressione del carburante non e' buona, che non arriva corrente sufficiente alla pompa di bassa pressione per il suo avvio e che il suo cablaggio elettrico e' in buono stato, il mio consiglio e' quello di provare a sostituire il cablaggio elettrico della pompa di bassa pressione.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il coperchio posto sulla scatola dei fusibili.3) Scollega il cablaggio elettrico.4) Rimuovi il fusibile della pompa di bassa pressione sostituendolo con uno nuovo e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Fusibile: dispositivo elettrico in grado di proteggere un circuito dalle sovracorrenti causate per esempio dai cortocircuiti. Pompa di bassa pressione: componente che ha il compito di inviare il carburante dal serbatoio del veicolo alla pompa di alta pressione.")
)

(defrule tensione-sensori-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ko)
=>
	(bind ?*domanda* "La cinghia di distribuzione e' in buono stato?")
	(bind ?*spiegazione* "Potrebbe essersi spezzata la cinghia di distribuzione mandando fuori fase il motore.")
	(bind ?*aiuto* "1) Stacca la batteria. 2) Metti il veicolo sul ponte elevatore.3) Rimuovi la ruota anteriore destra.4) Rimuovi il supporto motore destro.5) Rimuovi la cinghia accessori e la puleggia dell'albero motore.6) Rimuovi il coperchio posto sull'asse a camme.7) Controlla lo stato dell'intero kit distribuzione.")
	(bind ?*glossario* "Cinghia di distribuzione: costruita in gomma, e' molto silenziosa, ma non garantisce la durata, in quanto invecchiando, la gomma s'indurisce, rischiando di causare rotture improvvise della cinghia stessa con gravi conseguenze per il motore.")
)

(defrule cinghia-distribuzione-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ko)
	(distribuzione ko)
=>
	(bind ?*soluzione* "Sostituisci il kit distribuzione!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase non funzionano adeguatamente e che l'intero kit distribuzione o parte di esso non e' in buono stato, il mio consiglio e' quello di provare a sostituire l'intero kit distribuzione.")
	(bind ?*aiuto* "1) Stacca la batteria. 2) Metti il veicolo sul ponte elevatore.3) Rimuovi la ruota anteriore destra.4) Rimuovi il supporto motore destro.5) Rimuovi la cinghia accessori e la puleggia dell'albero motore.6) Rimuovi il coperchio posto sull'asse a camme.7) Metti in fase il motore inserendo una spina da 8 mm nell'albero motore e un'altra nell'asse a camme.8) Rimuovi l'intero kit distribuzione sostituendolo con uno nuovo e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Kit distribuzione: insieme degli organi meccanici predisposti al controllo dei gas che entrano ed escono nei cilindri.")
)

(defrule cinghia-distribuzione-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore gira)
	(tensione sensori ko)
	(distribuzione ok)
=>
	(bind ?*soluzione* "Sostituisci i sensori di giri e fase!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore gira, che i sensori di giri e di fase non funzionano adeguatamente e che il kit distribuzione e' in buono stato, il mio consiglio e' quello di provare a sostituire i sensori di giri e fase.")
	(bind ?*aiuto* "1) Stacca la batteria. 2) Metti il veicolo sul ponte elevatore.3) Rimuovi la ruota anteriore destra.4) Rimuovi il supporto motore destro.5) Rimuovi la cinghia accessori e la puleggia dell'albero motore.6) Rimuovi il coperchio posto sull'asse a camme.7) Metti in fase il motore inserendo una spina da 8 mm nell'albero motore e un'altra nell'asse a camme.8) Rimuovi i sensori di giri e fase sostituendoli con i nuovi e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Sensore di fase: sensore collegato all'albero a camme e che da' informazioni sulla posizione di tali camme; queste informazioni servono per iniettare il carburante nei cilindri al momento giusto e nella giusta sequenza. Sensore di giri: sensore che serve a determinare il regime di rotazione del motore leggendolo da una ruota fonica calettata sull'albero motore.")
)

(defrule tensione-batteria-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
	(tensione batteria ko)
=>
	(bind ?*soluzione* "Sostituisci la batteria!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore non gira e che il voltaggio della batteria e' inferiore a 12 volt, il mio consiglio e' quello di provare a sostituire la batteria.")
	(bind ?*aiuto* "1) Allenta il morsetto negativo, svita la vite e toglilo allontanandolo.2) Ripeti l'operazione con il morsetto positivo.3) Svita le viti di sostegno del supporto che blocca la batteria, toglilo e poi togli la batteria.4) Inserisci la nuova batteria esattamente nella stessa identica posizione di quella vecchia.5) Collega i morsetti e posiziona nuovamente il supporto che blocca la batteria fissandolo con le sue viti.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule tensione-batteria-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
	(tensione batteria ok)
=>
	(bind ?*domanda* "Avvia il motore controllando la tensione della batteria. Il valore misurato e' inferiore a 10 volt?")
	(bind ?*spiegazione* "Avviando il motore, il voltaggio di una batteria carica e funzionante non deve scendere sotto i 10 volt.")
	(bind ?*aiuto* "Collega il multimetro ai poli della batteria, avvia il motore e leggi il voltaggio sul monitor.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule tensione-avviamento-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
	(tensione batteria ok)
	(tensione avviamento ok)
=>
	(bind ?*domanda* "Avvia il motore controllando la tensione in arrivo sul motorino di avviamento dal blocchetto chiave. Il valore misurato e' circa 12 volt?")
	(bind ?*spiegazione* "Il motorino di avviamento potrebbe non girare correttamente.")
	(bind ?*aiuto* "Collega il multimetro al motorino di avviamento, avvia il motore e leggi il voltaggio sul monitor.")
	(bind ?*glossario* " Blocchetto chiave: blocco elettronico che impedisce l'avviamento del motore senza la giusta chiave. Motorino di avviamento: componente che permette al motore di raggiungere un numero di giri sufficienti all'autosostentamento.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule tensione-avviamento-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
	(tensione batteria ok)
	(tensione avviamento ko)
=>
	(bind ?*soluzione* "Sostituisci la batteria! ")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore non gira e che il voltaggio della batteria e' buono quando il veicolo e' spento ma cala non appena si prova ad avviare il motore, il mio consiglio e' quello di provare a sostituire la batteria.")
	(bind ?*aiuto* "1) Allenta il morsetto negativo, svita la vite e toglilo allontanandolo.2) Ripeti l'operazione con il morsetto positivo.3) Svita le viti di sostegno del supporto che blocca la batteria, toglilo e poi togli la batteria.4) Inserisci la nuova batteria esattamente nella stessa identica posizione di quella vecchia.5) Collega i morsetti e posiziona nuovamente il supporto che blocca la batteria fissandolo con le sue viti.")
	(bind ?*glossario* "Non e' presente alcun termine.")
)

(defrule tensione-blocchetto-chiave-non-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
	(tensione batteria ok)
	(tensione avviamento ok)
	(tensione blocchetto chiave ko)
=>
	(bind ?*soluzione* "Sostituisci la scatola dei fusibili presente nel vano motore!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore non gira, che il voltaggio della batteria e' buono sia quando il veicolo e' spento sia quando si prova ad avviare il motore e che non arriva corrente sufficiente al motorino di avviamento, il mio consiglio e' quello di provare a sostituire la scatola dei fusibili.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Rimuovi il coperchio posto sulla scatola dei fusibili.3) Scollega il cablaggio elettrico.4) Rimuovi la scatola dei fusibili sostituendola con la nuova e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Fusibili: dispositivi elettrici in grado di proteggere i circuiti dalle sovracorrenti.")
)

(defrule tensione-blocchetto-chiave-buona
	(preparazione-utente sufficiente | discreta)
	(veicolo non parte)
	(presenza carburante)
	(motore non gira)
	(tensione batteria ok)
	(tensione avviamento ok)
	(tensione blocchetto chiave ok)
=>
	(bind ?*soluzione* "Sostituisci il motorino di avviamento!")
	(bind ?*spiegazione* "Avendo dedotto dalle tue risposte che nel veicolo e' presente il carburante, che il motore non gira, che il voltaggio della batteria e' buono sia quando il veicolo e' spento sia quando si prova ad avviare il motore e che arriva corrente sufficiente al motorino di avviamento, il mio consiglio e' quello di provare a sostituire il motorino di avviamento.")
	(bind ?*aiuto* "1) Stacca la batteria.2) Metti il veicolo sul ponte elevatore.3) Rimuovi i bulloni che fissano il motorino di avviamento al blocco motore.4) Estrai il motorino di avviamento.5) Installa il nuovo motorino di avviamento e ripeti i passi precedenti in senso inverso.")
	(bind ?*glossario* "Motorino di avviamento: componente che permette al motore di raggiungere un numero di giri sufficienti all'autosostentamento.")
)
