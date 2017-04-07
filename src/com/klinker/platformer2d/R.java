package com.klinker.platformer2d;

public class R {
    public static class fonts {
        public static final String ROBOTO = "res/fonts/roboto";
        public static final String ROBOTO_BOLD = "res/fonts/roboto-bold";
        public static final String ROBOTO_LIGHT = "res/fonts/roboto-light";
    }
    public static class levels {
        public static final String LEVEL_DESIGN = "res/levels/level-design.txt";
        public static final String W02_LXX = "res/levels/w02-lxx.lvl";
    }
    public static class shaders {
        public static class frag {
            public static final String BASIC = "res/shaders/frag/basic.frag";
            public static final String COLOR_MULTIPLY = "res/shaders/frag/color-multiply.frag";
            public static final String COLOR_OVERLAY = "res/shaders/frag/color-overlay.frag";
            public static final String MOVE = "res/shaders/frag/move.frag";
        }
        public static class vert {
            public static final String BASIC = "res/shaders/vert/basic.vert";
        }
    }
    public static class strings {
        public static final String STORY_MODE = "Story Mode";
        public static final String MULTIPLAYER = "Multiplayer";
        public static final String SETTINGS = "Settings";
        public static final String VERSION = "Version 0.0.2 Alpha";
    }
    public static class textures {
        public static class bg {
            public static final String B01_00 = "res/textures/bg/01-00.png";
            public static final String B01_01 = "res/textures/bg/01-01.png";
            public static final String B01_02 = "res/textures/bg/01-02.png";
            public static final String B02_00 = "res/textures/bg/02-00.png";
            public static final String B02_01 = "res/textures/bg/02-01.png";
            public static final String B02_02 = "res/textures/bg/02-02.png";
            public static final String B03_00 = "res/textures/bg/03-00.png";
            public static final String B03_01 = "res/textures/bg/03-01.png";
            public static final String B03_02 = "res/textures/bg/03-02.png";
            public static final String B04_00 = "res/textures/bg/04-00.png";
            public static final String B04_01 = "res/textures/bg/04-01.png";
            public static final String B04_02 = "res/textures/bg/04-02.png";
            public static final String MAIN_MENU = "res/textures/bg/main-menu.png";
        }
        public static class buttons {
            public static final String REGULAR = "res/textures/buttons/regular.9";
        }
        public static final String COLLISION = "res/textures/collision.png";
        public static class enemies {
            public static final String KRAWLER = "res/textures/enemies/krawler.png";
        }
        public static class players {
            public static class hero01 {
                public static final String BODY = "res/textures/players/hero01/body.png";
            }
        }
        public static class tiles {
            public static class blocks {
                public static final String X0C_05 = "res/textures/tiles/blocks/x0C-05.png";
                public static final String X0C_06 = "res/textures/tiles/blocks/x0C-06.png";
                public static final String X0D_05 = "res/textures/tiles/blocks/x0D-05.png";
                public static final String X0D_06 = "res/textures/tiles/blocks/x0D-06.png";
                public static final String X0E_05 = "res/textures/tiles/blocks/x0E-05.png";
                public static final String X0E_06 = "res/textures/tiles/blocks/x0E-06.png";
                public static final String X0F_05 = "res/textures/tiles/blocks/x0F-05.png";
                public static final String X0F_06 = "res/textures/tiles/blocks/x0F-06.png";
                public static final String X10_05 = "res/textures/tiles/blocks/x10-05.png";
                public static final String X10_06 = "res/textures/tiles/blocks/x10-06.png";
                public static final String X10_08 = "res/textures/tiles/blocks/x10-08.png";
                public static final String X10_09 = "res/textures/tiles/blocks/x10-09.png";
                public static final String X11_08 = "res/textures/tiles/blocks/x11-08.png";
                public static final String X11_09 = "res/textures/tiles/blocks/x11-09.png";
            }
            public static class collectables {
                public static final String X0C_00 = "res/textures/tiles/collectables/x0C-00.png";
                public static final String X0C_01 = "res/textures/tiles/collectables/x0C-01.png";
                public static final String X0C_02 = "res/textures/tiles/collectables/x0C-02.png";
                public static final String X0C_03 = "res/textures/tiles/collectables/x0C-03.png";
                public static final String X0C_04 = "res/textures/tiles/collectables/x0C-04.png";
                public static final String X0D_00 = "res/textures/tiles/collectables/x0D-00.png";
                public static final String X0D_01 = "res/textures/tiles/collectables/x0D-01.png";
                public static final String X0D_02 = "res/textures/tiles/collectables/x0D-02.png";
                public static final String X0D_03 = "res/textures/tiles/collectables/x0D-03.png";
                public static final String X0D_04 = "res/textures/tiles/collectables/x0D-04.png";
                public static final String X0E_00 = "res/textures/tiles/collectables/x0E-00.png";
                public static final String X0E_01 = "res/textures/tiles/collectables/x0E-01.png";
                public static final String X0E_02 = "res/textures/tiles/collectables/x0E-02.png";
                public static final String X0E_03 = "res/textures/tiles/collectables/x0E-03.png";
                public static final String X0E_04 = "res/textures/tiles/collectables/x0E-04.png";
                public static final String X0F_00 = "res/textures/tiles/collectables/x0F-00.png";
                public static final String X0F_01 = "res/textures/tiles/collectables/x0F-01.png";
                public static final String X0F_02 = "res/textures/tiles/collectables/x0F-02.png";
                public static final String X0F_03 = "res/textures/tiles/collectables/x0F-03.png";
                public static final String X0F_04 = "res/textures/tiles/collectables/x0F-04.png";
                public static final String X10_00 = "res/textures/tiles/collectables/x10-00.png";
                public static final String X10_01 = "res/textures/tiles/collectables/x10-01.png";
                public static final String X10_02 = "res/textures/tiles/collectables/x10-02.png";
                public static final String X10_03 = "res/textures/tiles/collectables/x10-03.png";
                public static final String X10_04 = "res/textures/tiles/collectables/x10-04.png";
            }

            public static class environment {
                public static final String LAVA_SURFACE = "res/textures/tiles/environment/lava-surface.png";
                public static final String LAVA = "res/textures/tiles/environment/lava.png";
                public static final String MUD_SURFACE = "res/textures/tiles/environment/mud-surface.png";
                public static final String MUD = "res/textures/tiles/environment/mud.png";
                public static final String WATER_SURFACE = "res/textures/tiles/environment/water-surface.png";
                public static final String WATER = "res/textures/tiles/environment/water.png";
            }
            public static class ground {
                public static final String CLIFF_LEFT_W01 = "res/textures/tiles/ground/cliff-left-w01.png";
                public static final String CLIFF_LEFT_W02 = "res/textures/tiles/ground/cliff-left-w02.png";
                public static final String CLIFF_LEFT_W03 = "res/textures/tiles/ground/cliff-left-w03.png";
                public static final String CLIFF_LEFT_W04 = "res/textures/tiles/ground/cliff-left-w04.png";
                public static final String CLIFF_RIGHT_W01 = "res/textures/tiles/ground/cliff-right-w01.png";
                public static final String CLIFF_RIGHT_W02 = "res/textures/tiles/ground/cliff-right-w02.png";
                public static final String CLIFF_RIGHT_W03 = "res/textures/tiles/ground/cliff-right-w03.png";
                public static final String CLIFF_RIGHT_W04 = "res/textures/tiles/ground/cliff-right-w04.png";
                public static final String END_LEFT_COVERED_W01 = "res/textures/tiles/ground/end-left-covered-w01.png";
                public static final String END_LEFT_COVERED_W02 = "res/textures/tiles/ground/end-left-covered-w02.png";
                public static final String END_LEFT_COVERED_W03 = "res/textures/tiles/ground/end-left-covered-w03.png";
                public static final String END_LEFT_COVERED_W04 = "res/textures/tiles/ground/end-left-covered-w04.png";
                public static final String END_LEFT_W01 = "res/textures/tiles/ground/end-left-w01.png";
                public static final String END_LEFT_W02 = "res/textures/tiles/ground/end-left-w02.png";
                public static final String END_LEFT_W03 = "res/textures/tiles/ground/end-left-w03.png";
                public static final String END_LEFT_W04 = "res/textures/tiles/ground/end-left-w04.png";
                public static final String END_RIGHT_COVERED_W01 = "res/textures/tiles/ground/end-right-covered-w01.png";
                public static final String END_RIGHT_COVERED_W02 = "res/textures/tiles/ground/end-right-covered-w02.png";
                public static final String END_RIGHT_COVERED_W03 = "res/textures/tiles/ground/end-right-covered-w03.png";
                public static final String END_RIGHT_COVERED_W04 = "res/textures/tiles/ground/end-right-covered-w04.png";
                public static final String END_RIGHT_W01 = "res/textures/tiles/ground/end-right-w01.png";
                public static final String END_RIGHT_W02 = "res/textures/tiles/ground/end-right-w02.png";
                public static final String END_RIGHT_W03 = "res/textures/tiles/ground/end-right-w03.png";
                public static final String END_RIGHT_W04 = "res/textures/tiles/ground/end-right-w04.png";
                public static final String FILL_0_W01 = "res/textures/tiles/ground/fill-0-w01.png";
                public static final String FILL_0_W02 = "res/textures/tiles/ground/fill-0-w02.png";
                public static final String FILL_0_W03 = "res/textures/tiles/ground/fill-0-w03.png";
                public static final String FILL_0_W04 = "res/textures/tiles/ground/fill-0-w04.png";
                public static final String FILL_1_W01 = "res/textures/tiles/ground/fill-1-w01.png";
                public static final String FILL_1_W02 = "res/textures/tiles/ground/fill-1-w02.png";
                public static final String FILL_1_W03 = "res/textures/tiles/ground/fill-1-w03.png";
                public static final String FILL_1_W04 = "res/textures/tiles/ground/fill-1-w04.png";
                public static final String FILL_2_W01 = "res/textures/tiles/ground/fill-2-w01.png";
                public static final String FILL_2_W02 = "res/textures/tiles/ground/fill-2-w02.png";
                public static final String FILL_2_W03 = "res/textures/tiles/ground/fill-2-w03.png";
                public static final String FILL_2_W04 = "res/textures/tiles/ground/fill-2-w04.png";
                public static final String FLOATING_END_LEFT_W01 = "res/textures/tiles/ground/floating-end-left-w01.png";
                public static final String FLOATING_END_LEFT_W02 = "res/textures/tiles/ground/floating-end-left-w02.png";
                public static final String FLOATING_END_LEFT_W03 = "res/textures/tiles/ground/floating-end-left-w03.png";
                public static final String FLOATING_END_LEFT_W04 = "res/textures/tiles/ground/floating-end-left-w04.png";
                public static final String FLOATING_MIDDLE_LEFT_W01 = "res/textures/tiles/ground/floating-middle-left-w01.png";
                public static final String FLOATING_MIDDLE_LEFT_W02 = "res/textures/tiles/ground/floating-middle-left-w02.png";
                public static final String FLOATING_MIDDLE_LEFT_W03 = "res/textures/tiles/ground/floating-middle-left-w03.png";
                public static final String FLOATING_MIDDLE_LEFT_W04 = "res/textures/tiles/ground/floating-middle-left-w04.png";
                public static final String FLOATING_SINGLE_W01 = "res/textures/tiles/ground/floating-single-w01.png";
                public static final String FLOATING_SINGLE_W02 = "res/textures/tiles/ground/floating-single-w02.png";
                public static final String FLOATING_SINGLE_W03 = "res/textures/tiles/ground/floating-single-w03.png";
                public static final String FLOATING_SINGLE_W04 = "res/textures/tiles/ground/floating-single-w04.png";
                public static final String GROUND_RF_W01 = "res/textures/tiles/ground/ground-rf-w01.png";
                public static final String GROUND_RF_W02 = "res/textures/tiles/ground/ground-rf-w02.png";
                public static final String GROUND_RF_W03 = "res/textures/tiles/ground/ground-rf-w03.png";
                public static final String GROUND_RF_W04 = "res/textures/tiles/ground/ground-rf-w04.png";
                public static final String SINGLE_W01 = "res/textures/tiles/ground/single-w01.png";
                public static final String SINGLE_W02 = "res/textures/tiles/ground/single-w02.png";
                public static final String SINGLE_W03 = "res/textures/tiles/ground/single-w03.png";
                public static final String SINGLE_W04 = "res/textures/tiles/ground/single-w04.png";
                public static final String SLOPE_135_0_W01 = "res/textures/tiles/ground/slope-135-0-w01.png";
                public static final String SLOPE_135_0_W02 = "res/textures/tiles/ground/slope-135-0-w02.png";
                public static final String SLOPE_135_0_W03 = "res/textures/tiles/ground/slope-135-0-w03.png";
                public static final String SLOPE_135_0_W04 = "res/textures/tiles/ground/slope-135-0-w04.png";
                public static final String SLOPE_135_2_W01 = "res/textures/tiles/ground/slope-135-2-w01.png";
                public static final String SLOPE_135_2_W02 = "res/textures/tiles/ground/slope-135-2-w02.png";
                public static final String SLOPE_135_2_W03 = "res/textures/tiles/ground/slope-135-2-w03.png";
                public static final String SLOPE_135_2_W04 = "res/textures/tiles/ground/slope-135-2-w04.png";
                public static final String SLOPE_45_0_W01 = "res/textures/tiles/ground/slope-45-0-w01.png";
                public static final String SLOPE_45_0_W02 = "res/textures/tiles/ground/slope-45-0-w02.png";
                public static final String SLOPE_45_0_W03 = "res/textures/tiles/ground/slope-45-0-w03.png";
                public static final String SLOPE_45_0_W04 = "res/textures/tiles/ground/slope-45-0-w04.png";
                public static final String SLOPE_45_1_W01 = "res/textures/tiles/ground/slope-45-1-w01.png";
                public static final String SLOPE_45_1_W02 = "res/textures/tiles/ground/slope-45-1-w02.png";
                public static final String SLOPE_45_1_W03 = "res/textures/tiles/ground/slope-45-1-w03.png";
                public static final String SLOPE_45_1_W04 = "res/textures/tiles/ground/slope-45-1-w04.png";
                public static final String W01 = "res/textures/tiles/ground/w01.png";
                public static final String W02 = "res/textures/tiles/ground/w02.png";
                public static final String W03 = "res/textures/tiles/ground/w03.png";
                public static final String W04 = "res/textures/tiles/ground/w04.png";
            }
            public static class interatables {
                public static final String X09_0A = "res/textures/tiles/interatables/x09-0A.png";
                public static final String X09_0B = "res/textures/tiles/interatables/x09-0B.png";
                public static final String X0A_0A = "res/textures/tiles/interatables/x0A-0A.png";
                public static final String X0A_0B = "res/textures/tiles/interatables/x0A-0B.png";
                public static final String X0B_0A = "res/textures/tiles/interatables/x0B-0A.png";
                public static final String X0B_0B = "res/textures/tiles/interatables/x0B-0B.png";
                public static final String X0C_07 = "res/textures/tiles/interatables/x0C-07.png";
                public static final String X0C_08 = "res/textures/tiles/interatables/x0C-08.png";
                public static final String X0D_07 = "res/textures/tiles/interatables/x0D-07.png";
                public static final String X0D_08 = "res/textures/tiles/interatables/x0D-08.png";
                public static final String X0E_07 = "res/textures/tiles/interatables/x0E-07.png";
                public static final String X0E_08 = "res/textures/tiles/interatables/x0E-08.png";
                public static final String X0E_09 = "res/textures/tiles/interatables/x0E-09.png";
                public static final String X0F_07 = "res/textures/tiles/interatables/x0F-07.png";
                public static final String X0F_08 = "res/textures/tiles/interatables/x0F-08.png";
                public static final String X0F_09 = "res/textures/tiles/interatables/x0F-09.png";
                public static final String X10_07 = "res/textures/tiles/interatables/x10-07.png";
                public static final String X11_07 = "res/textures/tiles/interatables/x11-07.png";
                public static final String X12_07 = "res/textures/tiles/interatables/x12-07.png";
                public static final String X13_07 = "res/textures/tiles/interatables/x13-07.png";
            }
            public static class landscaping {
                public static final String X09_08 = "res/textures/tiles/landscaping/x09-08.png";
                public static final String X09_09 = "res/textures/tiles/landscaping/x09-09.png";
                public static final String X0A_08 = "res/textures/tiles/landscaping/x0A-08.png";
                public static final String X0A_09 = "res/textures/tiles/landscaping/x0A-09.png";
                public static final String X0B_08 = "res/textures/tiles/landscaping/x0B-08.png";
                public static final String X0B_09 = "res/textures/tiles/landscaping/x0B-09.png";
                public static final String X0C_09 = "res/textures/tiles/landscaping/x0C-09.png";
                public static final String X0D_09 = "res/textures/tiles/landscaping/x0D-09.png";
                public static final String X10_0A = "res/textures/tiles/landscaping/x10-0A.png";
                public static final String X10_0B = "res/textures/tiles/landscaping/x10-0B.png";
                public static final String X11_00 = "res/textures/tiles/landscaping/x11-00.png";
                public static final String X11_0A = "res/textures/tiles/landscaping/x11-0A.png";
                public static final String X11_0B = "res/textures/tiles/landscaping/x11-0B.png";
                public static final String X12_00 = "res/textures/tiles/landscaping/x12-00.png";
                public static final String X12_0A = "res/textures/tiles/landscaping/x12-0A.png";
                public static final String X12_0B = "res/textures/tiles/landscaping/x12-0B.png";
                public static final String X13_00 = "res/textures/tiles/landscaping/x13-00.png";
                public static final String X13_0A = "res/textures/tiles/landscaping/x13-0A.png";
                public static final String X13_0B = "res/textures/tiles/landscaping/x13-0B.png";
                public static final String X14_00 = "res/textures/tiles/landscaping/x14-00.png";
                public static final String X14_0A = "res/textures/tiles/landscaping/x14-0A.png";
                public static final String X14_0B = "res/textures/tiles/landscaping/x14-0B.png";
                public static final String X15_0A = "res/textures/tiles/landscaping/x15-0A.png";
                public static final String X15_0B = "res/textures/tiles/landscaping/x15-0B.png";
            }
            public static class plants {
                public static final String X09_00 = "res/textures/tiles/plants/x09-00.png";
                public static final String X09_01 = "res/textures/tiles/plants/x09-01.png";
                public static final String X09_02 = "res/textures/tiles/plants/x09-02.png";
                public static final String X09_03 = "res/textures/tiles/plants/x09-03.png";
                public static final String X09_04 = "res/textures/tiles/plants/x09-04.png";
                public static final String X09_05 = "res/textures/tiles/plants/x09-05.png";
                public static final String X09_06 = "res/textures/tiles/plants/x09-06.png";
                public static final String X09_07 = "res/textures/tiles/plants/x09-07.png";
                public static final String X0A_00 = "res/textures/tiles/plants/x0A-00.png";
                public static final String X0A_01 = "res/textures/tiles/plants/x0A-01.png";
                public static final String X0A_02 = "res/textures/tiles/plants/x0A-02.png";
                public static final String X0A_03 = "res/textures/tiles/plants/x0A-03.png";
                public static final String X0A_04 = "res/textures/tiles/plants/x0A-04.png";
                public static final String X0A_05 = "res/textures/tiles/plants/x0A-05.png";
                public static final String X0A_06 = "res/textures/tiles/plants/x0A-06.png";
                public static final String X0A_07 = "res/textures/tiles/plants/x0A-07.png";
                public static final String X0B_00 = "res/textures/tiles/plants/x0B-00.png";
                public static final String X0B_01 = "res/textures/tiles/plants/x0B-01.png";
                public static final String X0B_02 = "res/textures/tiles/plants/x0B-02.png";
                public static final String X0B_03 = "res/textures/tiles/plants/x0B-03.png";
                public static final String X0B_04 = "res/textures/tiles/plants/x0B-04.png";
                public static final String X0B_05 = "res/textures/tiles/plants/x0B-05.png";
                public static final String X0B_06 = "res/textures/tiles/plants/x0B-06.png";
                public static final String X0B_07 = "res/textures/tiles/plants/x0B-07.png";
                public static final String X11_01 = "res/textures/tiles/plants/x11-01.png";
                public static final String X11_02 = "res/textures/tiles/plants/x11-02.png";
                public static final String X11_03 = "res/textures/tiles/plants/x11-03.png";
                public static final String X11_04 = "res/textures/tiles/plants/x11-04.png";
                public static final String X12_01 = "res/textures/tiles/plants/x12-01.png";
                public static final String X12_02 = "res/textures/tiles/plants/x12-02.png";
                public static final String X12_03 = "res/textures/tiles/plants/x12-03.png";
                public static final String X12_04 = "res/textures/tiles/plants/x12-04.png";
                public static final String X13_01 = "res/textures/tiles/plants/x13-01.png";
                public static final String X13_02 = "res/textures/tiles/plants/x13-02.png";
                public static final String X13_03 = "res/textures/tiles/plants/x13-03.png";
                public static final String X13_04 = "res/textures/tiles/plants/x13-04.png";
                public static final String X14_01 = "res/textures/tiles/plants/x14-01.png";
                public static final String X14_02 = "res/textures/tiles/plants/x14-02.png";
                public static final String X14_03 = "res/textures/tiles/plants/x14-03.png";
                public static final String X15_00 = "res/textures/tiles/plants/x15-00.png";
                public static final String X15_01 = "res/textures/tiles/plants/x15-01.png";
                public static final String X15_02 = "res/textures/tiles/plants/x15-02.png";
                public static final String X15_03 = "res/textures/tiles/plants/x15-03.png";
                public static final String X15_04 = "res/textures/tiles/plants/x15-04.png";
            }
            public static class unlockable {
                public static final String X11_05 = "res/textures/tiles/unlockable/x11-05.png";
                public static final String X11_06 = "res/textures/tiles/unlockable/x11-06.png";
                public static final String X12_05 = "res/textures/tiles/unlockable/x12-05.png";
                public static final String X12_06 = "res/textures/tiles/unlockable/x12-06.png";
                public static final String X12_08 = "res/textures/tiles/unlockable/x12-08.png";
                public static final String X12_09 = "res/textures/tiles/unlockable/x12-09.png";
                public static final String X13_05 = "res/textures/tiles/unlockable/x13-05.png";
                public static final String X13_06 = "res/textures/tiles/unlockable/x13-06.png";
                public static final String X13_08 = "res/textures/tiles/unlockable/x13-08.png";
                public static final String X13_09 = "res/textures/tiles/unlockable/x13-09.png";
                public static final String X14_05 = "res/textures/tiles/unlockable/x14-05.png";
                public static final String X14_06 = "res/textures/tiles/unlockable/x14-06.png";
                public static final String X14_07 = "res/textures/tiles/unlockable/x14-07.png";
                public static final String X14_08 = "res/textures/tiles/unlockable/x14-08.png";
                public static final String X14_09 = "res/textures/tiles/unlockable/x14-09.png";
                public static final String X15_05 = "res/textures/tiles/unlockable/x15-05.png";
                public static final String X15_06 = "res/textures/tiles/unlockable/x15-06.png";
                public static final String X15_07 = "res/textures/tiles/unlockable/x15-07.png";
                public static final String X15_08 = "res/textures/tiles/unlockable/x15-08.png";
                public static final String X15_09 = "res/textures/tiles/unlockable/x15-09.png";
            }
        }
        public static class worlds {
            public static final String W01_BROWN = "res/textures/worlds/01-Brown.png";
            public static final String W02_BLUE = "res/textures/worlds/02-Blue.png";
            public static final String W03_RED = "res/textures/worlds/03-Red.png";
            public static final String W04_PURPLE = "res/textures/worlds/04-Purple.png";
        }
    }
}