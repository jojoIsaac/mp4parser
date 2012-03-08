package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoFile;
import com.googlecode.mp4parser.ByteBufferByteChannel;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.ldap.ExtendedRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.coremedia.iso.boxes.CastUtils.l2i;

public class ItemLocationBoxTest {


    @Test
    public void testSimpleRoundTrip() throws IOException {
        testSimpleRoundTrip(1, 2, 4, 8);
        testSimpleRoundTrip(2, 4, 8, 1);
        testSimpleRoundTrip(4, 8, 1, 2);
        testSimpleRoundTrip(8, 1, 2, 4);
    }

    public void testSimpleRoundTrip(int baseOffsetSize, int indexSize, int lengthSize, int offsetSize) throws IOException {
        ItemLocationBox ilocOrig = new ItemLocationBox();
        ilocOrig.setVersion(1);
        ilocOrig.setBaseOffsetSize(baseOffsetSize);
        ilocOrig.setIndexSize(indexSize);
        ilocOrig.setLengthSize(lengthSize);
        ilocOrig.setOffsetSize(offsetSize);
        ByteBuffer bb = ByteBuffer.allocate(l2i(ilocOrig.getSize()));
        ilocOrig.getBox(new ByteBufferByteChannel(bb));
        Assert.assertTrue(bb.remaining() == 0);
        bb.rewind();

        IsoFile isoFile = new IsoFile(new ByteBufferByteChannel(bb));

        ItemLocationBox iloc = (ItemLocationBox) isoFile.getBoxes().get(0);

        Assert.assertEquals(ilocOrig.getBaseOffsetSize(), iloc.getBaseOffsetSize());
        Assert.assertEquals(ilocOrig.getContentSize(), iloc.getContentSize());
        Assert.assertEquals(ilocOrig.getIndexSize(), iloc.getIndexSize());
        Assert.assertEquals(ilocOrig.getLengthSize(), iloc.getLengthSize());
        Assert.assertEquals(ilocOrig.getOffsetSize(), iloc.getOffsetSize());
        Assert.assertEquals(ilocOrig.getItems(), iloc.getItems());


    }


    @Test
    public void testSimpleRoundWithEntriesTrip() throws IOException {
        testSimpleRoundWithEntriesTrip(1, 2, 4, 8);
        testSimpleRoundWithEntriesTrip(2, 4, 8, 1);
        testSimpleRoundWithEntriesTrip(4, 8, 1, 2);
        testSimpleRoundWithEntriesTrip(8, 1, 2, 4);
    }

    public void testSimpleRoundWithEntriesTrip(int baseOffsetSize, int indexSize, int lengthSize, int offsetSize) throws IOException {
        ItemLocationBox ilocOrig = new ItemLocationBox();
        ilocOrig.setVersion(1);
        ilocOrig.setBaseOffsetSize(baseOffsetSize);
        ilocOrig.setIndexSize(indexSize);
        ilocOrig.setLengthSize(lengthSize);
        ilocOrig.setOffsetSize(offsetSize);
        ItemLocationBox.Item item = ilocOrig.createItem(12, 0, 13, 123, Collections.<ItemLocationBox.Extent>emptyList());
        ilocOrig.setItems(Collections.singletonList(item));
        ByteBuffer bb = ByteBuffer.allocate(l2i(ilocOrig.getSize()));
        ilocOrig.getBox(new ByteBufferByteChannel(bb));

        bb.rewind();

        IsoFile isoFile = new IsoFile(new ByteBufferByteChannel(bb));

        ItemLocationBox iloc = (ItemLocationBox) isoFile.getBoxes().get(0);

        Assert.assertEquals(ilocOrig.getBaseOffsetSize(), iloc.getBaseOffsetSize());
        Assert.assertEquals(ilocOrig.getContentSize(), iloc.getContentSize());
        Assert.assertEquals(ilocOrig.getIndexSize(), iloc.getIndexSize());
        Assert.assertEquals(ilocOrig.getLengthSize(), iloc.getLengthSize());
        Assert.assertEquals(ilocOrig.getOffsetSize(), iloc.getOffsetSize());
        Assert.assertEquals(ilocOrig.getItems(), iloc.getItems());


    }

    @Test
    public void testSimpleRoundWithEntriesAndExtentsTrip() throws IOException {
        testSimpleRoundWithEntriesAndExtentsTrip(1, 2, 4, 8);
        testSimpleRoundWithEntriesAndExtentsTrip(2, 4, 8, 1);
        testSimpleRoundWithEntriesAndExtentsTrip(4, 8, 1, 2);
        testSimpleRoundWithEntriesAndExtentsTrip(8, 1, 2, 4);
    }

    public void testSimpleRoundWithEntriesAndExtentsTrip(int baseOffsetSize, int indexSize, int lengthSize, int offsetSize) throws IOException {
        ItemLocationBox ilocOrig = new ItemLocationBox();
        ilocOrig.setVersion(1);
        ilocOrig.setBaseOffsetSize(baseOffsetSize);
        ilocOrig.setIndexSize(indexSize);
        ilocOrig.setLengthSize(lengthSize);
        ilocOrig.setOffsetSize(offsetSize);
        List<ItemLocationBox.Extent> extents = new LinkedList<ItemLocationBox.Extent>();
        ItemLocationBox.Extent extent = ilocOrig.createExtent(12, 13, 1);
        extents.add(extent);
        ItemLocationBox.Item item = ilocOrig.createItem(12, 0, 13, 123, extents);
        ilocOrig.setItems(Collections.singletonList(item));
        ByteBuffer bb = ByteBuffer.allocate(l2i(ilocOrig.getSize()));
        ilocOrig.getBox(new ByteBufferByteChannel(bb));

        bb.rewind();

        IsoFile isoFile = new IsoFile(new ByteBufferByteChannel(bb));

        ItemLocationBox iloc = (ItemLocationBox) isoFile.getBoxes().get(0);

        Assert.assertEquals(ilocOrig.getBaseOffsetSize(), iloc.getBaseOffsetSize());
        Assert.assertEquals(ilocOrig.getContentSize(), iloc.getContentSize());
        Assert.assertEquals(ilocOrig.getIndexSize(), iloc.getIndexSize());
        Assert.assertEquals(ilocOrig.getLengthSize(), iloc.getLengthSize());
        Assert.assertEquals(ilocOrig.getOffsetSize(), iloc.getOffsetSize());
        Assert.assertEquals(ilocOrig.getItems(), iloc.getItems());


    }

    @Test
    public void testExtent() throws IOException {
        testExtent(1, 2, 4, 8);
        testExtent(2, 4, 8, 1);
        testExtent(4, 8, 1, 2);
        testExtent(8, 1, 2, 4);
    }

    public void testExtent(int a, int b, int c, int d) throws IOException {
        ItemLocationBox iloc = new ItemLocationBox();
        iloc.setVersion(1);
        iloc.setBaseOffsetSize(a);
        iloc.setIndexSize(b);
        iloc.setLengthSize(c);
        iloc.setOffsetSize(d);
        ItemLocationBox.Extent e1 = iloc.createExtent(123, 124, 125);
        ByteBuffer bb = ByteBuffer.allocate(e1.getSize());
        e1.getContent(bb);
        Assert.assertTrue(bb.remaining() == 0);
        bb.rewind();
        ItemLocationBox.Extent e2 = iloc.createExtent(bb);

        Assert.assertEquals(e1, e2);


    }

    @Test
    public void testItem() throws IOException {
        testItem(1, 2, 4, 8);
        testItem(2, 4, 8, 1);
        testItem(4, 8, 1, 2);
        testItem(8, 1, 2, 4);
    }

    public void testItem(int a, int b, int c, int d) throws IOException {
        ItemLocationBox iloc = new ItemLocationBox();
        iloc.setVersion(1);
        iloc.setBaseOffsetSize(a);
        iloc.setIndexSize(b);
        iloc.setLengthSize(c);
        iloc.setOffsetSize(d);
        ItemLocationBox.Item e1 = iloc.createItem(65, 1, 0, 66, Collections.<ItemLocationBox.Extent>emptyList());
        ByteBuffer bb = ByteBuffer.allocate(e1.getSize());
        e1.getContent(bb);
        Assert.assertTrue(bb.remaining() == 0);
        bb.rewind();
        ItemLocationBox.Item e2 = iloc.createItem(bb);

        Assert.assertEquals(e1, e2);


    }

    @Test
    public void testItemVersionZero() throws IOException {
        testItemVersionZero(1, 2, 4, 8);
        testItemVersionZero(2, 4, 8, 1);
        testItemVersionZero(4, 8, 1, 2);
        testItemVersionZero(8, 1, 2, 4);
    }

    public void testItemVersionZero(int a, int b, int c, int d) throws IOException {
        ItemLocationBox iloc = new ItemLocationBox();
        iloc.setVersion(0);
        iloc.setBaseOffsetSize(a);
        iloc.setIndexSize(b);
        iloc.setLengthSize(c);
        iloc.setOffsetSize(d);
        ItemLocationBox.Item e1 = iloc.createItem(65, 0, 1, 66, Collections.<ItemLocationBox.Extent>emptyList());
        ByteBuffer bb = ByteBuffer.allocate(e1.getSize());
        e1.getContent(bb);
        Assert.assertTrue(bb.remaining() == 0);
        bb.rewind();
        ItemLocationBox.Item e2 = iloc.createItem(bb);

        Assert.assertEquals(e1, e2);


    }
}