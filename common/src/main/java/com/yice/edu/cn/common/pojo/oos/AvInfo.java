package com.yice.edu.cn.common.pojo.oos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvInfo {


    /**
     * streams : [{"time_base":"1/14112000","r_frame_rate":"0/0","start_pts":353600,"index":0,"channel_layout":"stereo","duration_ts":3527884800,"codec_name":"mp3","tags":{"encoder":"LAME3.99r"},"duration":"249.991837","start_time":"0.025057","bit_rate":"128000","disposition":{"dub":0,"karaoke":0,"default":0,"original":0,"visual_impaired":0,"forced":0,"attached_pic":0,"timed_thumbnails":0,"comment":0,"hearing_impaired":0,"lyrics":0,"clean_effects":0},"codec_tag":"0x0000","sample_rate":"44100","channels":2,"sample_fmt":"s16p","codec_time_base":"1/44100","side_data_list":[{"side_data_type":"Replay Gain"}],"codec_tag_string":"[0][0][0][0]","bits_per_sample":0,"avg_frame_rate":"0/0","codec_type":"audio","codec_long_name":"MP3 (MPEG audio layer 3)"}]
     * format : {"duration":"249.991837","start_time":"0.025057","bit_rate":"128013","size":"4000286","probe_score":51,"nb_programs":0,"format_long_name":"MP2/3 (MPEG audio layer 2/3)","nb_streams":1,"format_name":"mp3"}
     */
    private List<StreamsEntity> streams;
    private FormatEntity format;

    public void setStreams(List<StreamsEntity> streams) {
        this.streams = streams;
    }

    public void setFormat(FormatEntity format) {
        this.format = format;
    }

    public List<StreamsEntity> getStreams() {
        return streams;
    }

    public FormatEntity getFormat() {
        return format;
    }

    public static class StreamsEntity {

        /**
         * time_base : 1/14112000
         * r_frame_rate : 0/0
         * start_pts : 353600
         * index : 0
         * channel_layout : stereo
         * duration_ts : 3527884800
         * codec_name : mp3
         * tags : {"encoder":"LAME3.99r"}
         * duration : 249.991837
         * start_time : 0.025057
         * bit_rate : 128000
         * disposition : {"dub":0,"karaoke":0,"default":0,"original":0,"visual_impaired":0,"forced":0,"attached_pic":0,"timed_thumbnails":0,"comment":0,"hearing_impaired":0,"lyrics":0,"clean_effects":0}
         * codec_tag : 0x0000
         * sample_rate : 44100
         * channels : 2
         * sample_fmt : s16p
         * codec_time_base : 1/44100
         * side_data_list : [{"side_data_type":"Replay Gain"}]
         * codec_tag_string : [0][0][0][0]
         * bits_per_sample : 0
         * avg_frame_rate : 0/0
         * codec_type : audio
         * codec_long_name : MP3 (MPEG audio layer 3)
         */
        private String time_base;
        private String r_frame_rate;
        private int start_pts;
        private int index;
        private String channel_layout;
        private long duration_ts;
        private String codec_name;
        private TagsEntity tags;
        private String duration;
        private String start_time;
        private String bit_rate;
        private DispositionEntity disposition;
        private String codec_tag;
        private String sample_rate;
        private int channels;
        private String sample_fmt;
        private String codec_time_base;
        private List<Side_data_listEntity> side_data_list;
        private String codec_tag_string;
        private int bits_per_sample;
        private String avg_frame_rate;
        private String codec_type;
        private String codec_long_name;

        public void setTime_base(String time_base) {
            this.time_base = time_base;
        }

        public void setR_frame_rate(String r_frame_rate) {
            this.r_frame_rate = r_frame_rate;
        }

        public void setStart_pts(int start_pts) {
            this.start_pts = start_pts;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setChannel_layout(String channel_layout) {
            this.channel_layout = channel_layout;
        }

        public void setDuration_ts(long duration_ts) {
            this.duration_ts = duration_ts;
        }

        public void setCodec_name(String codec_name) {
            this.codec_name = codec_name;
        }

        public void setTags(TagsEntity tags) {
            this.tags = tags;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setBit_rate(String bit_rate) {
            this.bit_rate = bit_rate;
        }

        public void setDisposition(DispositionEntity disposition) {
            this.disposition = disposition;
        }

        public void setCodec_tag(String codec_tag) {
            this.codec_tag = codec_tag;
        }

        public void setSample_rate(String sample_rate) {
            this.sample_rate = sample_rate;
        }

        public void setChannels(int channels) {
            this.channels = channels;
        }

        public void setSample_fmt(String sample_fmt) {
            this.sample_fmt = sample_fmt;
        }

        public void setCodec_time_base(String codec_time_base) {
            this.codec_time_base = codec_time_base;
        }

        public void setSide_data_list(List<Side_data_listEntity> side_data_list) {
            this.side_data_list = side_data_list;
        }

        public void setCodec_tag_string(String codec_tag_string) {
            this.codec_tag_string = codec_tag_string;
        }

        public void setBits_per_sample(int bits_per_sample) {
            this.bits_per_sample = bits_per_sample;
        }

        public void setAvg_frame_rate(String avg_frame_rate) {
            this.avg_frame_rate = avg_frame_rate;
        }

        public void setCodec_type(String codec_type) {
            this.codec_type = codec_type;
        }

        public void setCodec_long_name(String codec_long_name) {
            this.codec_long_name = codec_long_name;
        }

        public String getTime_base() {
            return time_base;
        }

        public String getR_frame_rate() {
            return r_frame_rate;
        }

        public int getStart_pts() {
            return start_pts;
        }

        public int getIndex() {
            return index;
        }

        public String getChannel_layout() {
            return channel_layout;
        }

        public long getDuration_ts() {
            return duration_ts;
        }

        public String getCodec_name() {
            return codec_name;
        }

        public TagsEntity getTags() {
            return tags;
        }

        public String getDuration() {
            return duration;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getBit_rate() {
            return bit_rate;
        }

        public DispositionEntity getDisposition() {
            return disposition;
        }

        public String getCodec_tag() {
            return codec_tag;
        }

        public String getSample_rate() {
            return sample_rate;
        }

        public int getChannels() {
            return channels;
        }

        public String getSample_fmt() {
            return sample_fmt;
        }

        public String getCodec_time_base() {
            return codec_time_base;
        }

        public List<Side_data_listEntity> getSide_data_list() {
            return side_data_list;
        }

        public String getCodec_tag_string() {
            return codec_tag_string;
        }

        public int getBits_per_sample() {
            return bits_per_sample;
        }

        public String getAvg_frame_rate() {
            return avg_frame_rate;
        }

        public String getCodec_type() {
            return codec_type;
        }

        public String getCodec_long_name() {
            return codec_long_name;
        }

        public static class TagsEntity {
            /**
             * encoder : LAME3.99r
             */
            private String encoder;

            public void setEncoder(String encoder) {
                this.encoder = encoder;
            }

            public String getEncoder() {
                return encoder;
            }
        }

        public static class DispositionEntity {
            /**
             * dub : 0
             * karaoke : 0
             * default : 0
             * original : 0
             * visual_impaired : 0
             * forced : 0
             * attached_pic : 0
             * timed_thumbnails : 0
             * comment : 0
             * hearing_impaired : 0
             * lyrics : 0
             * clean_effects : 0
             */
            private int dub;
            private int karaoke;
            @SerializedName("default")
            private int defaultX;
            private int original;
            private int visual_impaired;
            private int forced;
            private int attached_pic;
            private int timed_thumbnails;
            private int comment;
            private int hearing_impaired;
            private int lyrics;
            private int clean_effects;

            public void setDub(int dub) {
                this.dub = dub;
            }

            public void setKaraoke(int karaoke) {
                this.karaoke = karaoke;
            }

            public void setDefaultX(int defaultX) {
                this.defaultX = defaultX;
            }

            public void setOriginal(int original) {
                this.original = original;
            }

            public void setVisual_impaired(int visual_impaired) {
                this.visual_impaired = visual_impaired;
            }

            public void setForced(int forced) {
                this.forced = forced;
            }

            public void setAttached_pic(int attached_pic) {
                this.attached_pic = attached_pic;
            }

            public void setTimed_thumbnails(int timed_thumbnails) {
                this.timed_thumbnails = timed_thumbnails;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public void setHearing_impaired(int hearing_impaired) {
                this.hearing_impaired = hearing_impaired;
            }

            public void setLyrics(int lyrics) {
                this.lyrics = lyrics;
            }

            public void setClean_effects(int clean_effects) {
                this.clean_effects = clean_effects;
            }

            public int getDub() {
                return dub;
            }

            public int getKaraoke() {
                return karaoke;
            }

            public int getDefaultX() {
                return defaultX;
            }

            public int getOriginal() {
                return original;
            }

            public int getVisual_impaired() {
                return visual_impaired;
            }

            public int getForced() {
                return forced;
            }

            public int getAttached_pic() {
                return attached_pic;
            }

            public int getTimed_thumbnails() {
                return timed_thumbnails;
            }

            public int getComment() {
                return comment;
            }

            public int getHearing_impaired() {
                return hearing_impaired;
            }

            public int getLyrics() {
                return lyrics;
            }

            public int getClean_effects() {
                return clean_effects;
            }
        }

        public static class Side_data_listEntity {
            /**
             * side_data_type : Replay Gain
             */
            private String side_data_type;

            public void setSide_data_type(String side_data_type) {
                this.side_data_type = side_data_type;
            }

            public String getSide_data_type() {
                return side_data_type;
            }
        }
    }

    public static class FormatEntity {
        /**
         * duration : 249.991837
         * start_time : 0.025057
         * bit_rate : 128013
         * size : 4000286
         * probe_score : 51
         * nb_programs : 0
         * format_long_name : MP2/3 (MPEG audio layer 2/3)
         * nb_streams : 1
         * format_name : mp3
         */
        private String duration;
        private String start_time;
        private String bit_rate;
        private String size;
        private int probe_score;
        private int nb_programs;
        private String format_long_name;
        private int nb_streams;
        private String format_name;

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setBit_rate(String bit_rate) {
            this.bit_rate = bit_rate;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setProbe_score(int probe_score) {
            this.probe_score = probe_score;
        }

        public void setNb_programs(int nb_programs) {
            this.nb_programs = nb_programs;
        }

        public void setFormat_long_name(String format_long_name) {
            this.format_long_name = format_long_name;
        }

        public void setNb_streams(int nb_streams) {
            this.nb_streams = nb_streams;
        }

        public void setFormat_name(String format_name) {
            this.format_name = format_name;
        }

        public String getDuration() {
            return duration;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getBit_rate() {
            return bit_rate;
        }

        public String getSize() {
            return size;
        }

        public int getProbe_score() {
            return probe_score;
        }

        public int getNb_programs() {
            return nb_programs;
        }

        public String getFormat_long_name() {
            return format_long_name;
        }

        public int getNb_streams() {
            return nb_streams;
        }

        public String getFormat_name() {
            return format_name;
        }
    }
}
