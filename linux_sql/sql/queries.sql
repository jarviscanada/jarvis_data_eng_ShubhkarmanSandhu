--First query--

        SELECT cpu_number,id,total_mem FROM host_info ORDER BY cpu_number,total_mem DESC ;

--Second query--

        CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
            $$
        BEGIN
        RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
        END;
        $$
        LANGUAGE PLPGSQL;

        CREATE OR REPLACE FUNCTION avg_p(total decimal,free decimal ) RETURNS decimal AS
        $$
        BEGIN
        RETURN ROUND((total/1024.0 -free)/(total/1024.0)*100);
        END;
        $$
        LANGUAGE PLPGSQL;

        SELECT host_id,host_name, round5(hu.timestamp) as timestamp,avg_p(total_mem,memory_free) as avg_used_mem_percentage
        FROM host_info hi JOIN host_usage hu ON hi.id=hu.host_id;

--Third query--

        SELECT temp.host_id, temp.timestamp, COUNT(*) as num_data_points
        FROM(
            SELECT hu.host_id, round5(hu.timestamp) as timestamp
            FROM host_info hi
            JOIN host_usage hu ON hi.id=hu.host_id)
        AS temp
        GROUP BY temp.timestamp, temp.host_id ORDER BY temp.timestamp;