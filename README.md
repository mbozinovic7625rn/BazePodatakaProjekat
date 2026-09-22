# Research Laboratory Database — Databases Course Project

Team project for the *Uvod u upravljanje bazama podataka* (Databases) course at Računarski fakultet (RAF), Belgrade: a complete **MySQL** database for managing research laboratories — labs, researchers, experiment designers and performers, experiments, sessions, tools, and resource inventories — plus a **Java desktop client** on top of it.

## What's inside

```
├── dijagrami/            # EER diagram + specification documents (PDF)
├── query/
│   ├── create.sql        # Full schema — ~20 tables with FK constraints (laboratorija_db)
│   ├── inserts.sql       # Sample data
│   ├── view.sql          # Views
│   ├── functions.sql     # Stored functions
│   ├── transactions.sql  # Transaction scenarios
│   ├── mihailo_bozinovic.sql   # Per-author query assignments
│   └── milica_samardzic.sql
└── java/uubp_java/       # Java Swing desktop app (DTO model + views over JDBC)
```

## Highlights

- **Schema design from specification**: EER modeling of a many-entity domain (experiments linked to designers, performers, tool types, and resources through junction tables), documented in the PDFs under `dijagrami/`.
- **Beyond CRUD**: views, stored functions, and explicit transaction scenarios, not just table definitions.
- **Working client**: a Java Swing application (registration, account management, browsing of labs and researchers) connected to the database.

## Running it

1. Run `query/create.sql` on a MySQL server, then `query/inserts.sql` for sample data.
2. Apply `view.sql`, `functions.sql` as needed.
3. Configure the connection in `java/uubp_java/src/config/Config.java` and start the app from `Launcher`/`Main`.

## Authors

Mihailo Božinović · Milica Samardžić — Računarski fakultet (RAF), Belgrade
