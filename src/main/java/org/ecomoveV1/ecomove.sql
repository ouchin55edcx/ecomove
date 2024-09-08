-- Enum Tables :

CREATE TYPE transport_type AS ENUM ('PLANE', 'TRAIN', 'BUS') ;
CREATE TYPE partner_status AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED') ;
CREATE TYPE contract_status AS ENUM ('ACTIVE', 'TERMINATED', 'SUSPENDED') ;
CREATE TYPE reduction_type AS ENUM ('PERCENTAGE', 'FIXED_AMOUNT');
CREATE TYPE offer_status AS ENUM ('ACTIVE', 'EXPIRED', 'SUSPENDED');
CREATE TYPE ticket_status AS ENUM ('SOLD', 'CANCELLED', 'PENDING');


-- Partner Table :

CREATE TABLE IF NOT EXISTS partner (
    id UUID PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    commercial_contact VARCHAR(255),
    transport_type transport_type NOT NULL,
    geographical_zone VARCHAR(255),
    special_conditions TEXT,
    status partner_status NOT NULL,
    creation_date DATE NOT NULL
);


-- Contract Table :

CREATE TABLE IF NOT EXISTS contract (
    id UUID PRIMARY KEY,
    partner_id UUID NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    special_rate DECIMAL(10, 2),
    agreement_conditions TEXT,
    renewable BOOLEAN NOT NULL,
    status contract_status NOT NULL,
    FOREIGN KEY (partner_id) REFERENCES partner(id)

);

-- PromotionalOffer Table

CREATE TABLE promotional_offer (
    id UUID PRIMARY KEY,
    contract_id UUID NOT NULL,
    offer_name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reduction_type reduction_type NOT NULL,
    reduction_value DECIMAL(10, 2) NOT NULL,
    conditions TEXT,
    status offer_status NOT NULL,
    FOREIGN KEY (contract_id) REFERENCES contract(id)
);

-- Ticket Table

CREATE TABLE ticket (
    id UUID PRIMARY KEY,
    contract_id UUID NOT NULL,
    transport_type transport_type NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    sale_price DECIMAL(10, 2) NOT NULL,
    sale_date TIMESTAMP NOT NULL,
    status ticket_status NOT NULL,
    FOREIGN KEY (contract_id) REFERENCES contract(id)

);

-- Customer Table

CREATE TABLE Customer(
    id UUID PRIMARY KEY ,
    first_name VARCHAR(12),
    last_name VARCHAR(12),
    email VARCHAR(35),
    phone_number VARCHAR(13)
);





