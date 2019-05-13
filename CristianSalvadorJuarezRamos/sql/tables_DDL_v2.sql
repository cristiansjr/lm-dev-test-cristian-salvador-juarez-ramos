CREATE TABLE client.cli_client (
	cli_id serial NOT NULL,
	cli_user varchar(50) NULL,
	cli_password varchar(50) NULL
	CONSTRAINT cli_cliente_pk PRIMARY KEY (cli_id)
);

CREATE TABLE client.cli_status (
	cli_status_id varchar(1) NOT NULL,
	cli_status_description varchar(100) NULL,
	CONSTRAINT cli_status_pk PRIMARY KEY (cli_status_id)
);

CREATE TABLE client.cli_client_status (
	cli_client_status_id serial NOT NULL,
	cli_client_id int4 NULL,
	cli_login_attempts int4 NULL,
	cli_client_ip varchar(15) NULL,
	cli_status varchar(1) NULL,
	CONSTRAINT cli_client_status_pk PRIMARY KEY (cli_client_status_id),
	CONSTRAINT cli_client_x_client_status_fk FOREIGN KEY (cli_client_id) REFERENCES client.cli_client(cli_id),
	CONSTRAINT cli_status_x_client_status_fk FOREIGN KEY (cli_status) REFERENCES client.cli_status(cli_status_id)
);

CREATE TABLE client.cli_benefit (
	cli_benefit_id serial NOT NULL,
	cli_id int4 NULL,
	cli_id_benefit int4 NULL,
	cli_benefit_authorization varchar NULL,
	CONSTRAINT cli_benefit_pk PRIMARY KEY (cli_benefit_id),
	CONSTRAINT cli_benefit_un UNIQUE (cli_id,cli_id_benefit),
	CONSTRAINT cli_benefit_cli_client_fk FOREIGN KEY (cli_id) REFERENCES client.cli_client(cli_id),
	CONSTRAINT cli_benefit_cli_client_benefit_fk FOREIGN KEY (cli_id_benefit) REFERENCES client.cli_client(cli_id)
);

CREATE TABLE personal_account.pea_personal_account (
	pea_id serial NOT NULL,
	pea_cli_id int4 NOT NULL,
	pea_create_date date NOT NULL,
	pea_ammount numeric(20,2) NULL,
	CONSTRAINT pea_personal_account_pk PRIMARY KEY (pea_id),
	CONSTRAINT pea_client_x_personal_account_fk FOREIGN KEY (pea_cli_id) REFERENCES client.cli_client(cli_id)
);

CREATE TABLE personal_account.pea_personal_account_transaction (
	pea_transaction_id serial NOT NULL,
	pea_id int4 NOT NULL,
	pea_authorization_number varchar NOT NULL,
	pea_transaction_ammount numeric(20,2) NOT NULL,
	pea_transaction_date timestamp NOT NULL,
	pea_transaction_type varchar(1) NOT NULL,
	CONSTRAINT pea_personal_account_transaction_pk PRIMARY KEY (pea_transaction_id),
	CONSTRAINT pea_personal_account_x_personal_account_transaction_fk FOREIGN KEY (pea_id) REFERENCES personal_account.pea_personal_account(pea_id)
);

CREATE TABLE credit_card.crc_credit_card (
	crc_id serial NOT NULL,
	crc_cli_id int4 NOT NULL,
	crc_create_date date NOT NULL,
	crc_credit_card_number varchar(24) NOT NULL,
	crc_credit_card_frachise varchar(20) NOT NULL,
	crc_credit_card_payment_date date NOT NULL,
	crc_credit_limit numeric(20,2) NOT NULL,
	CONSTRAINT crc_credit_card_pk PRIMARY KEY (crc_id),
	CONSTRAINT crc_client_x_credit_card_fk FOREIGN KEY (crc_cli_id) REFERENCES client.cli_client(cli_id)
);

CREATE TABLE credit_card.crc_credit_card_transaction (
	crc_transaction_id serial NOT NULL,
	crc_id int4 NOT NULL,
	crc_authorization_number varchar NOT NULL,
	crc_transaction_ammount numeric(20,2) NOT NULL,
	crc_transaction_date timestamp NOT NULL,
	crc_transaction_type varchar(1) NOT NULL,
	CONSTRAINT crc_credit_card_transaction_pk PRIMARY KEY (crc_transaction_id),
	CONSTRAINT crc_credit_card_x_credit_card_transaction_fk FOREIGN KEY (crc_id) REFERENCES credit_card.crc_credit_card(crc_id)
);

CREATE TABLE loan_account.loa_loan_account (
	loa_id serial NOT NULL,
	loa_cli_id int4 NOT NULL,
	loa_create_date date NOT NULL,
	loa_ammount numeric(20,2) NOT NULL,
	loa_payment_date date NOT NULL,
	CONSTRAINT loa_loan_account_pk PRIMARY KEY (loa_id),
	CONSTRAINT loa_client_x_loan_account_fk FOREIGN KEY (loa_cli_id) REFERENCES client.cli_client(cli_id)
);

CREATE TABLE loan_account.loa_loan_account_transaction (
	loa_transaction_id serial NOT NULL,
	loa_id int4 NOT NULL,
	loa_authorization_number varchar NOT NULL,
	loa_transaction_ammount numeric(20,2) NOT NULL,
	loa_transaction_date timestamp NOT NULL,
	loa_transaction_type varchar(1) NOT NULL,
	CONSTRAINT loa_loan_account_transaction_pk PRIMARY KEY (loa_transaction_id),
	CONSTRAINT loa_loan_account_x_loan_account_transaction_fk FOREIGN KEY (loa_id) REFERENCES loan_account.loa_loan_account(loa_id)
);

CREATE TABLE client.cli_client_status_log (
	cli_client_status_id serial NOT NULL,
	cli_client_id int4 NULL,
	cli_login_attempts int4 NULL,
	cli_client_ip varchar(15) NULL,
	cli_status varchar(1) NULL,
	cli_usr_op varchar(50) NULL,
	cli_operation varchar(1) NULL,
	cli_date_op timestamp
);