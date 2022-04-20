CREATE TABLE IF NOT EXISTS currency(
    id UUID PRIMARY KEY,
    name_crypto VARCHAR(80) NOT NULL,
    code VARCHAR(80) NOT NULL,
	created_at TIMESTAMP
);

INSERT INTO currency(name, code, created_at) VALUES('Bitcoin', 'BTC', current_timestamp);
INSERT INTO currency(name, code, created_at) VALUES('Ethereum', 'ETH', current_timestamp);
INSERT INTO currency(name, code, created_at) VALUES('Tether', 'USDT', current_timestamp);
INSERT INTO currency(name, code, created_at) VALUES('USD Coin', 'USDC', current_timestamp);
INSERT INTO currency(name, code, created_at) VALUES('SOLANA', 'SOL', current_timestamp);