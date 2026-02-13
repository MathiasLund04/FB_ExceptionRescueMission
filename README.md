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
  1. Tomt input ved kaptan- og skibsnavn - Programmet accepterer ikke tomme felter og kræver gyldigt input
  2. Bogstaver som menu-valg - Når der skal skrives tal, kastes InputMismatchException og brugeren får besked: "Du skal skrive et tal"
  3. Negativt antal reservedele ved handel - validateTrade() kaster IllegalArgumentException med besked: "Antal reservedele kan ikke være negativt"
  4. Ikke nok reservedele til shield-køb - validateTrade() kaster InvalidTradeException når man prøver at købe shield uden nok dele
  5. Ikke nok brændstof til omvej - takeDetour() kaster IllegalArgumentException: "Ikke nok brandstof til at tage en længere omvej"
  6. Kritisk lav integritet efter storm - Hvis integritet falder under 20, kastes CriticalStatusException og spillet slutter
  7. Motoren genstart efter 2 fejlede forsøg - Efter 2 fejlede genstarts kastes CriticalStatusException: "Motor er i kritisk tilstand"
