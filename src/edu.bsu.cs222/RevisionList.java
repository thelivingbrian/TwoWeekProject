package edu.bsu.cs222;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RevisionList {
    private final int REVISIONS_WANTED = 10;
    private int numOfRevs;
    private Revision[] setOfRevisions;
    private NodeList revisionNodeList;

    public RevisionList(NodeList xmlRevElements){
        revisionNodeList = xmlRevElements;
        setNumOfRevs();
        parseRevisions();
    }

    private void setNumOfRevs() {
        if (revisionNodeList.getLength() <= REVISIONS_WANTED) {
            numOfRevs = revisionNodeList.getLength();
        } else {
            numOfRevs = REVISIONS_WANTED;
        }
        setOfRevisions = new Revision[numOfRevs];
    }

    private void parseRevisions(){
        for(int i=0; i<numOfRevs; i++){
            Node node = revisionNodeList.item(i);
            Element e = (Element)node;
            setOfRevisions[i] = makeRevision(e);
        }
    }

    private Revision makeRevision(Element e) {
        return new Revision.RevisionBuilder().buildFromElement(e).build();
    }

    public Revision[] getArray() { return this.setOfRevisions; }

    public Revision revisionAtIndex(int index) { return setOfRevisions[index]; }

    public int getNumOfRevs() { return numOfRevs; }
}
