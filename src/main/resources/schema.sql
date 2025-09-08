CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(40) NOT NULL UNIQUE,
    login VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(100),
    birthday DATE,
    password VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS films (
    film_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description TEXT,
    release_date DATE,
    duration INT,
    rating VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    status VARCHAR(20),
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (friend_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS film_like (
    film_id INT,
    user_id INT,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

MERGE INTO genres (genre_id, name) VALUES
(1, 'Comedy'),
(2, 'Drama'),
(3, 'Cartoon'),
(4, 'Thriller'),
(5, 'ADocumentary'),
(6, 'Action_Movie');