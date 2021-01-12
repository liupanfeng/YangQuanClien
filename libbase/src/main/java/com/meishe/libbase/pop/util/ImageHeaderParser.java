package com.meishe.libbase.pop.util;


import com.meishe.libbase.pop.enums.ImageType;

import java.io.IOException;
import java.io.InputStream;

import static com.meishe.libbase.pop.enums.ImageType.GIF;
import static com.meishe.libbase.pop.enums.ImageType.JPEG;
import static com.meishe.libbase.pop.enums.ImageType.PNG;
import static com.meishe.libbase.pop.enums.ImageType.PNG_A;
import static com.meishe.libbase.pop.enums.ImageType.UNKNOWN;


/**
 * Description: copy from Glide.
 * 标题图片解析器类
 * Title image parser class
 */
public class ImageHeaderParser {
    private static final int GIF_HEADER = 0x474946;
    private static final int PNG_HEADER = 0x89504E47;
    /**
     * The Exif magic number.
     * Exif幻数
     */
    static final int EXIF_MAGIC_NUMBER = 0xFFD8;
    /*
    * WebP-related
    * "RIFF"
    * */
    private static final int RIFF_HEADER = 0x52494646;
    /*
    * "WEBP"
    * */
    private static final int WEBP_HEADER = 0x57454250;
    /*
    * "VP8" null.
    * */
    private static final int VP8_HEADER = 0x56503800;
    private static final int VP8_HEADER_MASK = 0xFFFFFF00;
    private static final int VP8_HEADER_TYPE_MASK = 0x000000FF;
    // 'X'
    private static final int VP8_HEADER_TYPE_EXTENDED = 0x00000058;
    // 'L'
    private static final int VP8_HEADER_TYPE_LOSSLESS = 0x0000004C;
    private static final int WEBP_EXTENDED_ALPHA_FLAG = 1 << 4;
    private static final int WEBP_LOSSLESS_ALPHA_FLAG = 1 << 3;

    /**
     * Gets image type.
     * 得到图像类型
     * @param is the is
     * @return the image type 图像类型
     * @throws IOException the io exception
     */
    static ImageType getImageType(InputStream is) throws IOException {
        Reader reader = new StreamReader(is);
        final int firstTwoBytes = reader.getUInt16();

        // JPEG.
        if (firstTwoBytes == EXIF_MAGIC_NUMBER) {
            return JPEG;
        }

        final int firstFourBytes = (firstTwoBytes << 16 & 0xFFFF0000) | (reader.getUInt16() & 0xFFFF);
        // PNG.
        if (firstFourBytes == PNG_HEADER) {
            /*
            * See: http://stackoverflow.com/questions/2057923/how-to-check-a-png-for-grayscale-alpha
            * 参见:http://stackoverflow.com/questions/2057923/how-to-check-a-png-for-grayscale-alpha
            * -color-type
            * 颜色类型
            * */

            reader.skip(25 - 4);
            int alpha = reader.getByte();
            /*
            * A RGB indexed PNG can also have transparency. Better safe than sorry!
            * 一个RGB索引的PNG也可以有透明度。安全总比遗憾好!
            * */
            return alpha >= 3 ? PNG_A : PNG;
        }

        /*
        * GIF from first 3 bytes.
        * GIF从前3个字节
        * */
        if (firstFourBytes >> 8 == GIF_HEADER) {
            return GIF;
        }

        /*
        * WebP (reads up to 21 bytes). See https://developers.google.com/speed/webp/docs/riff_container for details.
        * ：WebP(读取最多21个字节)。参见https://developers.google.com/speed/webp/docs/riff_container了解详细信息。
        * */
        if (firstFourBytes != RIFF_HEADER) {
            return UNKNOWN;
        }
        /*
        * Bytes 4 - 7 contain length information. Skip these.
        * 字节4 - 7包含长度信息。跳过这些
        * */
        reader.skip(4);
        final int thirdFourBytes =
                (reader.getUInt16() << 16 & 0xFFFF0000) | (reader.getUInt16() & 0xFFFF);
        if (thirdFourBytes != WEBP_HEADER) {
            return UNKNOWN;
        }
        final int fourthFourBytes =
                (reader.getUInt16() << 16 & 0xFFFF0000) | (reader.getUInt16() & 0xFFFF);
        if ((fourthFourBytes & VP8_HEADER_MASK) != VP8_HEADER) {
            return UNKNOWN;
        }
        if ((fourthFourBytes & VP8_HEADER_TYPE_MASK) == VP8_HEADER_TYPE_EXTENDED) {
            /*
            * Skip some more length bytes and check for transparency/alpha flag.
            * 跳过一些长度字节，检查透明度/alpha标志。
            * */
            reader.skip(4);
            return (reader.getByte() & WEBP_EXTENDED_ALPHA_FLAG) != 0 ? ImageType.WEBP_A : ImageType.WEBP;
        }
        if ((fourthFourBytes & VP8_HEADER_TYPE_MASK) == VP8_HEADER_TYPE_LOSSLESS) {
            /*
            * See chromium.googlesource.com/webm/libwebp/+/master/doc/webp-lossless-bitstream-spec.txt for more info.
            * 更多信息请参见chromium.googlesource.com/webm/libwebp/+/master/doc/webp-loss -bitstream-spec.txt。
            * */
            reader.skip(4);
            return (reader.getByte() & WEBP_LOSSLESS_ALPHA_FLAG) != 0 ? ImageType.WEBP_A : ImageType.WEBP;
        }
        is.close();
        return ImageType.WEBP;
    }
    private interface Reader {
        /**
         * Gets u int 16.
         * 得到u int 16
         * @return the u int 16
         * @throws IOException the io exception
         */
        int getUInt16() throws IOException;

        /**
         * Gets u int 8.
         * 得到u int 8
         * @return the u int 8
         * @throws IOException the io exception
         */
        short getUInt8() throws IOException;

        /**
         * Skip long.
         * 跳过长
         * @param total the total 总数
         * @return the long 长
         * @throws IOException the io exception
         */
        long skip(long total) throws IOException;

        /**
         * Read int.
         * 读取int
         * @param buffer    the buffer 缓冲
         * @param byteCount the byte count 字节计数
         * @return the int int
         * @throws IOException the io exception
         */
        int read(byte[] buffer, int byteCount) throws IOException;

        /**
         * Gets byte.
         * 得到字节
         * @return the byte  字节
         * @throws IOException the io exception
         */
        int getByte() throws IOException;
    }
    private static final class StreamReader implements Reader {
        private final InputStream is;
        /*
        * Motorola / big endian byte order.
        * 摩托罗拉/大端字节顺序
        * */
        StreamReader(InputStream is) {
            this.is = is;
        }

        @Override
        public int getUInt16() throws IOException {
            return (is.read() << 8 & 0xFF00) | (is.read() & 0xFF);
        }

        @Override
        public short getUInt8() throws IOException {
            return (short) (is.read() & 0xFF);
        }

        @Override
        public long skip(long total) throws IOException {
            if (total < 0) {
                return 0;
            }

            long toSkip = total;
            while (toSkip > 0) {
                long skipped = is.skip(toSkip);
                if (skipped > 0) {
                    toSkip -= skipped;
                } else {
                    /*
                    * Skip has no specific contract as to what happens when you reach the end of the stream. To differentiate between temporarily not having more data and
                    * having finished the stream, we read a single byte when we fail to skip any amount of data.
                    * Skip对于到达流的末尾时会发生什么没有明确的约定。区分暂时没有更多数据和
                    * 流完成后，我们读取单个字节时，我们没有跳过任何数量的数据
                    * */
                    int testEofByte = is.read();
                    if (testEofByte == -1) {
                        break;
                    } else {
                        toSkip--;
                    }
                }
            }
            return total - toSkip;
        }

        @Override
        public int read(byte[] buffer, int byteCount) throws IOException {
            int toRead = byteCount;
            int read;
            while (toRead > 0 && ((read = is.read(buffer, byteCount - toRead, toRead)) != -1)) {
                toRead -= read;
            }
            return byteCount - toRead;
        }

        @Override
        public int getByte() throws IOException {
            return is.read();
        }
    }
}
