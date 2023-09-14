package tischkicker.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Turnier {
    private String startdatum;
    private String enddatum;
    @Column(name = "spiele")
    private String spiele;

    @Column(name= "gespielt")
    private boolean gespielt;
    @Transient
    private int [] spieleIDs;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public boolean isGespielt() {
        return gespielt;
    }

    public void setGespielt(boolean gespielt) {
        this.gespielt = gespielt;
    }

    public String getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(String startdatum) {
        this.startdatum = startdatum;
    }

    public int getID() {
        return id;
    }

    public String getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(String enddatum) {
        this.enddatum = enddatum;
    }

    public void setSpieleIDs(int [] spiele){
        List<String> strings = Arrays.stream(spiele).boxed().map(id -> Integer.toString(id))
                .collect(Collectors.toList());
        this.spiele = String.join(",", strings);
        this.spieleIDs = spiele;
    }

    public int[] getSpieleIDs(){
        if (spieleIDs == null) {
            return Arrays.stream(spiele.split(",")).mapToInt(Integer::parseInt).toArray();
        }
        return spieleIDs;
    }



}