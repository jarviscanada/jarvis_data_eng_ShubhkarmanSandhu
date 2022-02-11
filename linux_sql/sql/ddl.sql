\c host_agent;
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id SERIAL NOT NULL,
    host_name VARCHAR(200) NOT NULL,
    cpu_number INT NOT NULL,
    cpu_architecture VARCHAR(15) NOT NULL,
    cpu_model VARCHAR(200) NOT NULL,
    cpu_mhz DECIMAL NOT NULL,
    L2_cache INT NOT NULL,
    total_mem BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(host_name)
    );
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    timestamp TIMESTAMP NOT NULL,
    host_id SERIAL NOT NULL,
    memory_free INT NOT NULL,
    cpu_idle INT NOT NULL,
    cpu_kernel INT NOT NULL,
    disk_io INT NOT NULL,
    disk_available INT NOT NULL,
    FOREIGN KEY(host_id)
    REFERENCES PUBLIC.host_info (id)
    );

\q