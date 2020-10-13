DROP TABLE vet_specialties IF EXISTS;
DROP TABLE vets IF EXISTS;
DROP TABLE specialties IF EXISTS;
DROP TABLE visits IF EXISTS;
DROP TABLE hotels IF EXISTS;
DROP TABLE pets IF EXISTS;
DROP TABLE types IF EXISTS;
DROP TABLE owners IF EXISTS;
DROP TABLE donations IF EXISTS;
DROP TABLE causes IF EXISTS;
DROP TABLE ejercicios IF EXISTS;


CREATE TABLE vets (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30)
);
CREATE INDEX vets_last_name ON vets (last_name);

CREATE TABLE specialties (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX specialties_name ON specialties (name);

CREATE TABLE vet_specialties (
  vet_id       INTEGER NOT NULL,
  specialty_id INTEGER NOT NULL
);
ALTER TABLE vet_specialties ADD CONSTRAINT fk_vet_specialties_vets FOREIGN KEY (vet_id) REFERENCES vets (id);
ALTER TABLE vet_specialties ADD CONSTRAINT fk_vet_specialties_specialties FOREIGN KEY (specialty_id) REFERENCES specialties (id);

CREATE TABLE types (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX types_name ON types (name);

CREATE TABLE owners (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
CREATE INDEX owners_last_name ON owners (last_name);

CREATE TABLE pets (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  birth_date DATE,
  type_id    INTEGER NOT NULL,
  owner_id   INTEGER NOT NULL
);

CREATE TABLE hotels (
	id INTEGER IDENTITY PRIMARY KEY,
	pet_id INTEGER NOT NULL,
	details VARCHAR(30),
	start_date_book DATE,
	end_date_book DATE
);

ALTER TABLE pets ADD CONSTRAINT fk_pets_owners FOREIGN KEY (owner_id) REFERENCES owners (id);
ALTER TABLE pets ADD CONSTRAINT fk_pets_types FOREIGN KEY (type_id) REFERENCES types (id);
CREATE INDEX pets_name ON pets (name);

CREATE TABLE visits (
  id          INTEGER IDENTITY PRIMARY KEY,
  pet_id      INTEGER NOT NULL,
  visit_date  DATE,
  description VARCHAR(255)
);
CREATE TABLE causes (
  id          INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  budget  INTEGER,
  organization  VARCHAR(255)
);

CREATE TABLE donations (
  id          INTEGER IDENTITY PRIMARY KEY,
  client VARCHAR(255),
  donation_date  DATE,
  amount  INTEGER,
  cause_id INTEGER,
);
ALTER TABLE donations ADD CONSTRAINT fk_donations_causes FOREIGN KEY (cause_id) REFERENCES causes (id);
CREATE INDEX donations_causes_id ON donations (cause_id);

CREATE TABLE ejercicios (
  id	INTEGER IDENTITY PRIMARY KEY,
  nombre VARCHAR(255),
  tipo VARCHAR(255),
  material VARCHAR(255),
  objetivo_tecnico VARCHAR(255),
  objetivo_tactico VARCHAR(255),
  descripcion VARCHAR(255)
)
