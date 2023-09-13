package tischkicker.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Turnier {
    private Date startdatum;
    private Date enddatum;
    @Column(name = "spiele")
    private String spiele;

    @Column(name= "gespielt")
    private boolean gespielt;
    private int [] spieleIDs;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    public boolean isGespielt() {
        return gespielt;
    }

    public void setGespielt(boolean gespielt) {
        this.gespielt = gespielt;
    }

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public int getID() {
        return ID;
    }

    public Date getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(Date enddatum) {
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