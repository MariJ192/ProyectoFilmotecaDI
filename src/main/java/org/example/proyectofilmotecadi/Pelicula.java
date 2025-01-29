package org.example.proyectofilmotecadi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.*;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Pelicula {
        private final IntegerProperty id = new SimpleIntegerProperty();
        private final StringProperty title = new SimpleStringProperty();
        private final IntegerProperty year = new SimpleIntegerProperty();
        private final StringProperty description = new SimpleStringProperty();
        private final DoubleProperty rating = new SimpleDoubleProperty();
        private final StringProperty poster = new SimpleStringProperty();
        private final StringProperty director = new SimpleStringProperty();


        public Pelicula() {

        }

        public Pelicula(int id, String title, int year, String description, double rating, String poster, String director) {
            this.id.set(id);
            this.title.set(title);
            this.year.set(year);
            this.description.set(description);
            this.rating.set(rating);
            this.poster.set(poster);
            this.director.set(director);
        }

        public int getId() {
            return id.get();
        }

        public IntegerProperty idProperty() {
            return id;
        }


        public void setId(int id) {
            this.id.set(id);
        }

        public String getTitle() {
            return title.get();
        }

        public StringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public int getYear() {
            return year.get();
        }

        public IntegerProperty yearProperty() {
            return year;
        }

        public void setYear(int year) {
            this.year.set(year);
        }

        public String getDescription() {
            return description.get();
        }

        public StringProperty descriptionProperty() {
            return description;
        }

        public void setDescription(String description) {
            this.description.set(description);
        }

        public Double getRating() {
            return rating.get();
        }

        public DoubleProperty ratingProperty() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating.set(rating);
        }

        public String getPoster() {
            return poster.get();
        }

        public StringProperty posterProperty() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster.set(poster);
        }
        public String getDirector() {
            return director.get();
        }
        public StringProperty directorProperty() {
            return director;
        }
        public void setDirector(String director) {
            this.director.set(director);
        }


        @Override
        public String toString() {
            return "Pelicula{" +
                    "id=" + id +
                    ", title=" + title +
                    ", year=" + year +
                    ", description=" + description +
                    ", rating=" + rating +
                    ", poster=" + poster +
                    '}';
        }
    }


