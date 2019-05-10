CREATE TABLE cliente.cli_client (
	cli_id serial NOT NULL,
	cli_user varchar(50) NULL,
	cli_password varchar(50) NULL
	CONSTRAINT cli_cliente_pk PRIMARY KEY (cli_id)
);

CREATE TABLE client.cli_client_status (
	ccs_client_status_id serial NOT NULL,
	ccs_client_id int4 NULL,
	ccs_login_attempts int4 NULL,
	ccs_client_ip varchar(15) NULL,
	ccs_status varchar(1) NULL,
	CONSTRAINT cli_client_status_pk PRIMARY KEY (ccs_client_status_id),
	CONSTRAINT cli_client_x_client_status_fk FOREIGN KEY (ccs_client_id) REFERENCES client.cli_client(cli_id)
	CONSTRAINT cli_status_x_client_status_fk FOREIGN KEY (ccs_status) REFERENCES client.cli_status(cli_status_id);
);

CREATE TABLE client.cli_status (
	cli_status_id varchar(1) NOT NULL,
	cli_status_description varchar(100) NULL,
	CONSTRAINT cli_status_pk PRIMARY KEY (cli_status_id)
);
