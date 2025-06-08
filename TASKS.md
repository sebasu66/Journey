# Development Tasks

- [ ] Identify all RNG tables in **Tome of Adventure Design**.
- [ ] Skim the book for every numbered table or multi-step generation process relevant to random encounters and events, then list their names, numbering, and approximate headings or line ranges.
- [ ] Extend `server/tools/parseTables.js` so it can detect multiple tables and export them.
- [ ] Add a configuration mapping table names to desired output filenames (e.g. `"Table 1-28: Random Acts" -> randomActs.json`).
- [ ] Ensure the parser exports clean JSON arrays for each detected table.
- [ ] Create unit tests validating that each table is extracted correctly and the resulting JSON files have the expected number of entries and formatting.
- [ ] Update server data loading to dynamically import all generated JSON files and organize them in an object keyed by table name.
- [ ] Add a route `GET /api/random/:tableName` that returns a random entry from the specified table, with error handling for unknown tables.
- [ ] Update Swagger/OpenAPI documentation to describe the new endpoint and any specific endpoints that remain.
- [ ] Expand server integration tests with Supertest for each endpoint and error case.
- [ ] Document new setup steps in the README for running the parser, starting the server, accessing Swagger docs, and running tests.
- [ ] Refactor parsing and server logic as needed, adding helper modules and comments.
- [ ] (Optional) Explore additional book sections and evaluate performance/caching as tables grow.
