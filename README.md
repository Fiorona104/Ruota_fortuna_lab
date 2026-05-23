
INTRODUZIONE

LUCKY HELL è un’app Android sviluppata utilizzando Kotlin e Android Studio.  
L’obiettivo principale dell’app è rendere più dinamica, casuale e divertente la scelta degli studenti da interrogare in classe attraverso una ruota animata.
L’app è stata creata come esercitazione pratica per imparare meglio lo sviluppo Android e la programmazione in Kotlin.
Durante lo sviluppo sono state utilizzate diverse funzioni importanti delle app mobile, come animazioni, audio, grafica personalizzata e interazione con l’utente.


DESCRIZIONE

L’app presenta una ruota grafica suddivisa in spicchi colorati.  
Ogni spicchio contiene il nome di uno studente.

Quando l’utente preme il pulsante “Gira”, la ruota:
- inizia a ruotare con un’animazione;
- riproduce un effetto sonoro;
- rallenta gradualmente;
- si ferma casualmente su uno dei nomi presenti.

Una volta terminata la rotazione:
- viene mostrato il nome estratto;
- viene riprodotto un suono finale;
- compaiono coriandoli animati;
- il nome viene rimosso dalla ruota;
- il risultato viene salvato nello storico, visibile sotto il bottone “Reset”

Questo sistema permette di evitare ripetizioni e rende l’estrazione più equa e casuale.


FUNZIONALITÀ PRINCIPALI

Ruota grafica personalizzata
La ruota è stata creata tramite una View personalizzata (`RuotaView.kt`) utilizzando il Canvas di Android.

Ogni spicchio:
- ha un colore alternato;
- contiene il nome dello studente;
- viene disegnato dinamicamente in base al numero di elementi presenti.

Rotazione casuale
La rotazione della ruota avviene tramite animazioni Android.

Ad ogni click:
- viene generato un numero casuale;
- la ruota effettua diversi giri completi;
- si ferma in una posizione diversa ogni volta. 

Questo rende impossibile prevedere il risultato.

Calcolo del vincitore
Il vincitore viene determinato tramite:
- l’angolo finale della ruota;
- il numero totale di spicchi;
- la posizione in cui si ferma la ruota. 

L’app divide il cerchio in sezioni uguali e identifica lo spicchio in cui la ruota si ferma.

Storico risultati
L’app salva tutti i nomi già usciti in uno storico visualizzato a schermo.

Questo permette di:
- controllare l’ordine delle estrazioni;
- evitare dubbi sui risultati precedenti.

Reset completo
Il pulsante Reset:
- ripristina tutti i nomi iniziali;
- riporta la ruota alla posizione iniziale;
- cancella lo storico;
- resetta il testo del risultato.

Questa funzione consente di riutilizzare facilmente l’app più volte.

Sistema audio
L’app usa suoni diversi per rendere l’esperienza più coinvolgente.

- Sigla iniziale
Parte quando si apre l’app.

- Suono di rotazione
Parte quando la ruota inizia a girare.

- Suono del risultato
Parte quando la ruota si ferma e viene mostrato il nome del vincitore.

