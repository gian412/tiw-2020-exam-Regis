# Requirements

---

Versione pure HTML  
Un’applicazione web consente la gestione di trasferimenti di denaro online da un conto a un altro. Un utente ha
un nome, un codice e uno o più conti correnti. Un conto ha un codice, un saldo, e i trasferimenti fatti (in uscita) e
ricevuti (in ingresso) dal conto. Un trasferimento ha una data, un importo, un conto di origine e un conto di
destinazione. Quando l’utente accede all’applicazione appare una pagina LOGIN per la verifica delle credenziali.
In seguito all’autenticazione dell’utente appare l’HOME page che mostra l’elenco dei suoi conti. Quando l’utente seleziona un conto, appare una pagina STATO DEL CONTO che mostra i dettagli del conto e la lista dei movimenti
in entrata e in uscita, ordinati per data discendente. La pagina contiene anche una form per ordinare un
trasferimento. La form contiene i campi: codice utente destinatario, codice conto destinatario, causale e importo.
All’invio della form con il bottone INVIA, l’applicazione controlla che il conto di destinazione appartenga all’utente
specificato e che il conto origine abbia un saldo superiore o uguale all’importo del trasferimento. In caso di mancanza di anche solo una condizione, l’applicazione mostra una pagina con un avviso di fallimento che spiega il motivo del mancato trasferimento. In caso di verifica di entrambe le condizioni, l’applicazione deduce l’importo
dal conto origine, aggiunge l’importo al conto destinazione e mostra una pagina CONFERMA TRASFERIMENTO che
presenta i dati del conto di origine e destinazione, con i rispettivi saldi aggiornati. L’applicazione deve garantire
l’atomicità del trasferimento: ogni volta che il conto di destinazione viene addebitato il conto di origine deve
essere accreditato e viceversa.

# Structural Design

***

## Data Structure

##### Schema creation:

```sql
CREATE SCHEMA `money_transfer`;
```

##### Users table creation:

```sql
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `username` varchar(64) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
);
```

##### Accounts table creation:

```sql
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `balance` decimal(9,2) unsigned NOT NULL DEFAULT '0.00',
  `owner` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ownership_idx` (`owner`),
  CONSTRAINT `ownership` 
    FOREIGN KEY (`owner`) 
    REFERENCES `user` (`id`) 
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);
```

##### Transfers table creation:

```sql
CREATE TABLE `transfer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` decimal(9,2) unsigned NOT NULL,
  `origin` int NOT NULL,
  `destination` int NOT NULL,
  `causal` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `origin_of_transfer_idx` (`origin`),
  KEY `destination_of_transfer_idx` (`destination`),
  CONSTRAINT `destination_of_transfer` 
    FOREIGN KEY (`destination`) 
    REFERENCES `account` (`id`) 
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `origin_of_transfer` 
   FOREIGN KEY (`origin`) 
   REFERENCES `account` (`id`) 
   ON DELETE CASCADE
   ON UPDATE NO ACTION
);
```

***

***

## Components

- ##### Model Objects (Beans)
  
  - User
  
  - Account
  
  - Transfer

- ##### Data Access Objects (Classes)
  
  - AnonymousUserDAO
  
  - UserDAO
  
  - AccountDAO
  
  - TransferDAO

- ##### Controllers (Servlet)
  
  - GoToWelcome
  
  - GetResources
  
  - CheckLogin
  
  - CreateUser
  
  - GoToLogin
  
  - GoToSignUp
  
  - GoToHome
  
  - GoToAccountStatus
  
  - MakeTransfer
  
  - Logout

- ##### Views (Templates) & component
  
  - welcome
  
  - login
  
  - signUp
  
  - home
  
  - accountStatus
  
  - transferError
  
  - transferSuccessful