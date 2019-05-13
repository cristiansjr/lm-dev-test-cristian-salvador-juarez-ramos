create or replace function client.cli_login_function(p_user varchar, p_password varchar, p_ip varchar) 
returns integer as $$
declare 
	response integer;
	passed boolean;
	id_client integer;
	status_client varchar;
	login_attmpts integer;
	begin
		response := -1;
		status_client := 'U';
		login_attmpts := 0;
		select cli_id into id_client
		  from client.cli_client
		 where cli_user = $1;
		if id_client is null then
			response :=  1;
		else
			select cli_status, cli_login_attempts into status_client, login_attmpts
			  from client.cli_client_status
			 where cli_client_id = id_client;
			if login_attmpts is null then
				login_attmpts := 0;
			end if;
			if status_client is null or status_client = 'A' then
				select (cli_password = $2) into passed
	    		  from client.cli_client
		    	 where cli_user = $1;
		    	if passed then
		    		if status_client is null then
		    			INSERT INTO client.cli_client_status (cli_client_id, cli_login_attempts, cli_client_ip, cli_status) VALUES(id_client, 0, p_ip, 'A');
		    		else
						UPDATE client.cli_client_status SET cli_login_attempts= 0, cli_client_ip=p_ip, cli_status='A' WHERE cli_client_id=id_client;
		    		end if;
					response :=  0;
		    	else
		    		if status_client is null then
		    			INSERT INTO client.cli_client_status (cli_client_id, cli_login_attempts, cli_client_ip, cli_status) VALUES(id_client, 1, p_ip, 'A');
		    		else
						if (login_attmpts + 1) = 5 then
							UPDATE client.cli_client_status SET cli_login_attempts= (login_attmpts + 1), cli_client_ip=p_ip, cli_status='M' WHERE cli_client_id=id_client;
							response :=  4;
						else
							UPDATE client.cli_client_status SET cli_login_attempts= (login_attmpts + 1), cli_client_ip=p_ip, cli_status='A' WHERE cli_client_id=id_client;
							response :=  3;
						end if;
		    		end if;
		    	end if;
	    	else 
	    		response :=  2;
			end if;
		end if;
		return response;
	exception
		when others then
			raise notice '% %', SQLERRM, SQLSTATE;
			return -1;
	end;
$$ language plpgsql;