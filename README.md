* **Hvordan programmet køres**
    - Brugeren får vist en overskuelig menu og ud fra det kan vælge hvad der kommer til at ske fremover ved at indsende deres input de relevante steder under kørsel af programmet.

* **Hvilke menupunkter der findes**
    - Der er menupunkter for hvert event.
    - Hver menu har valgmuligheder der alle består af tal. Hvis ikke man vælger et af valgmulighederne vil der kastes en exception og menuen vil køre indtal den får gyldigt input.

* **Hvilke exceptions du har lavet, og hvor de kastes og fanges**
    - Vi har lavet to exceptions, 'CriticalStatusException' og 'InvalidTradeException'.
    - CriticalStatusException kastes efter hvert event i Service for at tjekke om rumrejsen kan fortsætte, den bliver fanget i Main.
    - InvalidTradeException kastes ved validateTrade og fanges i de metoder hvori den bliver kaldt.
  
* **En kort testliste med 6 til 10 punkter over hvad du selv har prøvet**
    - I Main når programmet spørg om Kaptaijn og skibs navn har vi testet at man ikke kan efterlade fæltet tomt
    - Vi har testet at man ikke kan skrive bogstaver når der skal bruges tal.
    - Vi har testet at man skriver et tal som ikke er gyldigt
    - 