package xyz.jilulu.jamesji.bilifun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MuseMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muse_member);
        String[] museMemberWhoStartedThisActivity = getIntent().getStringExtra(Intent.EXTRA_TEXT).split("0");
        String ja = museMemberWhoStartedThisActivity[0];
        String hi = museMemberWhoStartedThisActivity[1];
        MuseMember liveMuseMember = new MuseMember(ja);
        RelativeLayout commonParent = (RelativeLayout) findViewById(R.id.relLayout);
        TextView largeTitle = ((TextView) commonParent.findViewById(R.id.museMemberName));
        TextView subtitle1 = ((TextView) commonParent.findViewById(R.id.museMemberNameHiragana));
        TextView subtitle2 = ((TextView) commonParent.findViewById(R.id.museMemberNameRomaji));
        ImageView avatar = ((ImageView) commonParent.findViewById(R.id.museMemberProfileImage));
        TextView basicTextView = (TextView) commonParent.findViewById(R.id.basicsText);
        TextView mangaTextView = (TextView) commonParent.findViewById(R.id.mangaText);
        TextView animeTextView = (TextView) commonParent.findViewById(R.id.animeText);
        largeTitle.setText(ja);
        subtitle1.setText(hi);
        subtitle2.setText(liveMuseMember.getRomaji());
        avatar.setImageResource(liveMuseMember.getResID());
        basicTextView.setText(liveMuseMember.getBasics());
        mangaTextView.setText(liveMuseMember.getMangaText());
        animeTextView.setText(liveMuseMember.getAnimeText());
    }



    class MuseMember {
        private String jaName, romaji, basics, mangaText, animeText;
        private int resID;

        public MuseMember(String ja) {
            jaName = ja;
            init();
        }

        private void init() {
            switch (jaName) {
                case "高坂 穂乃果":
                    romaji = "Kōsaka Honoka";
                    resID = R.drawable.honoka;
                    basics = "16歲，高中2年級生。生日8月3日，獅子座，血型為O型。身高157厘米，B78/W58/H82。第一人稱為「我」（わたし Watashi）或「穗乃果」（ほのか）。\n偶像活動的發起人，個性直率熱情、對任何事都全力以赴的努力家。家中經營日式傳統甜點的老店（和菓子屋）「穗村」（穂むら Homura）。分隊「Printemps」的隊長。[3]";
                    mangaText = "原先為劍道部成員，在一年級時得知了廢校的消息，為打響學校知名度於劍道大賽的新人個人賽中獲得優勝，希望能藉此增加入學學生，阻止廢校，但來年的入學學生並未因此增加。二年級時退出劍道部，決心成立偶像部。\n與小鳥、海未、繪里四人是童年玩伴，後來在國中時認識了花陽跟小凜。\n所屬的家庭創下了祖母、母親、孫女三代都曾就讀同一間學校的歷史紀錄。\n與動畫版相比，與妹妹雪穗的感情相當良好，二人在家裡幾乎是黏在一起。";
                    animeText = "與小鳥、海未三人是童年玩伴。因為學校將被廢校，然後從妹妹得知有校園偶像的存在，在看到UTX的校園偶像的PV影像後，提出了組建自己學校的校園偶像來拯救學校，學園偶像團體μ's的發起人兼初期成員。因為家裡為和式點心店的關係，很少能接觸到麵包，因此非常喜歡吃麵包。\n於第2期成為了學生會會長。因為怕自己再次給大家添麻煩而不想參加第2屆的LoveLive！，但在其他成員的說服之下決心參加，並以拿下冠軍為目標。";
                    break;
                case "南 ことり":
                    romaji = "Minami Kotori";
                    resID = R.drawable.kotori;
                    basics = "16歲，高中2年級生。生日9月12日，處女座，血型為O型。身高159厘米，B80/W58/H80。第一人稱為「我」（わたし Watashi）。\n與穗乃果是竹馬之交。音乃木坂學院理事長的女兒。\n性格相當溫柔、卻未流於優柔寡斷，一旦下定決心跟目標後，意志力會相當堅強；周遭對她的普遍印象是有著天然呆屬性的傻女孩。\n雖然對於偶像活動沒自信，不過很喜歡唱歌和跳舞。得意與擅長的東西是畫插圖以及裁縫。[3]";
                    mangaText = "與穗乃果、海未和繪里四人是童年玩伴。偶像部初期成員，被穗乃果以「可穿上任何喜歡的服裝」的誘因加入μ's。";
                    animeText = "與穗乃果、海未三人是童年玩伴。在穗乃果提出學園偶像的計畫時最先同意，並勸誘海未一同加入。 μ's的初期成員。\n同時也是秋葉原的招牌女僕服務生「Minalinsky」，但並未讓身旁的穗乃果等人知曉。於動畫第六話有短暫出現小鳥當女僕時所拍的照片，於第九話被眾人發現。\n在μ's還是三人成員的時期被招攬去女僕咖啡店打工。認為自己沒有甚麼特別的地方，因此希望能藉由在咖啡店的打工來改變自己。\n在繪里的建議下擔任《Wonder zone》的作詞。\n於動畫第1期第12話向大家坦白要出國留學一事，於最終話在出發前夕，在穗乃果的挽留下取消。\n在第2期裡擔任學生會的秘書跟文書，相較於嚴格監督穗乃果的海未，她是處於白臉跟寵愛孩子般的溫柔一方。";
                    break;
                case "園田 海未":
                    romaji = "Sonoda Umi";
                    resID = R.drawable.umi;
                    basics = "16歲，高中2年級生。生日3月15日，雙魚座，血型為A型。身高159厘米，B76/W58/H80。第一人稱為「我」（わたし Watashi）。\n與穗乃果、南小鳥是竹馬之交。生於一個從曾祖母一代開始就定居的日本舞蹈世家，在女子學校的環境中長大，是個標準的大和撫子。\n雖然長相成熟美麗並給人有種老成的氛圍，不過對於決勝負的事情（比賽）卻抱持著成為No.1的好勝性格。\n分隊「Lily white」的隊長。";
                    mangaText = "一年級時與穗乃果一同帶領劍道部。在穗乃果提出退出劍道部成立偶像部的宣言時十分生氣，因此發生爭吵。後因看到穗乃果等人的努力而和解，並加入偶像部。\n有些兒時的回憶畫面顯示出她與穗乃果從相當小的時後就已經認識，而小鳥顯然沒那麼早參與到兩人的生活圈裡。";
                    animeText = "弓道部成員。與穗乃果、小鳥三人是童年玩伴。個性上對熟識的人會趨於穩重，但是需要做出表白的公眾場合則會極度害羞怕生，μ's的初期成員。\n與漫畫版相反，穗乃果早已跟小鳥與其他女孩玩在一起，海未躲在一旁觀看她們，後來穗乃果發現了海未後，將她強行加入她們的行列。\nμ's裡早期的舞步構想者、兼任團體內的體能訓練教練，在繪裡加入後專心於團體的體能訓練及規劃。\n於第2期成為學生會副會長，督促愛玩的穗乃果好好主持學生會。在第2期第2話動畫裡透露，是個登山狂熱者；合宿的時候提出了運動量非常大的體能練習計劃。\n不擅長玩抽鬼牌遊戲，動畫中與劇場版時皆出現與兩位二年級成員抽鬼牌的場景，但因易於把內心的想法表露於外，遊玩時從未贏過。";
                    break;
                case "小泉 花陽":
                    romaji = "Koizumi Hanayo";
                    resID = R.drawable.hanayo;
                    basics = "15歲，高中1年級生。生日1月17日，摩羯座，血型為B型。身高156厘米，B82/W60/H83。第一人稱為「我」（わたし Watashi）。\n擅於聽人講話、不擅於跟人對談的害羞內向少女。非常擅長和小朋友玩，因此她認為自己非常適合當幼兒園老師和育兒工作者。喜歡白飯，在劇場版裏甚至因為在外國沒有飯吃而哭泣。周圍都稱呼為「花陽親」（かよちん Kayochin）。[3]";
                    mangaText = "穗乃果等人的國中學妹，跟凜一樣報考音乃木坂學院。在穗乃果剛開始偶像活動時加入。\n由於性向上相當內向及被動，很容易被想到甚麼就做甚麼的小凜拖著跑。\n在國中跟上高中的初期，後面的髮型是集中脖子中央的收束短髮，漫畫第二集(第八話)後短髮有開始留長的跡象，使得她的容貌跟動畫版極度相似。\n雖然在行動上很容易被小凜牽著鼻子走，但是無損於二人的濃厚感情。";
                    animeText = "自小學起便認識凜，當時就希望想成為偶像，隨著成長就把這願望壓在心裡。\n於學校中擔任飼育委員，照顧學校飼養的一公一母羊駝。\n戴著眼鏡，生性膽小、沒有自信，從小就憧憬著偶像的她因凜和真姬的鼓勵，而鼓起勇氣加入μ's，加入後在表演、練習場合會使用隱形眼鏡，平日生活偶爾會把眼鏡戴回來。\n說謊的時候會有交叉手指的習慣，雖然平日的姓格很文靜怕生，但是遇到與偶像相關的話題會變得相當積極與狂熱。\n因對偶像的理解不下於部長妮可，在三年級成員畢業的同時成為偶像研究部部長。";
                    break;
                case "星空 凛":
                    romaji = "Hoshizora Rin";
                    resID = R.drawable.rin;
                    basics = "15歲，高中1年級生。生日11月1日，天蠍座，血型為A型。身高155厘米，B75/W59/H80。第一人稱為「凜」（りん Rin）。\n是個障礙跑和足球都很擅長的運動型少女、身體柔韌性卻很差。做事比較遲鈍也有點愛哭，不過是個元氣十足的女孩[3]。有時句尾會加上「喵」（にゃ Nya），喜歡的食物是拉麵。";
                    mangaText = "性格跟表現幾乎與穗乃果一樣，甚至比後者還要熱情奔放，非常喜歡親近穗乃果，與花陽的感情也相當深厚。\n穗乃果等人的國中學妹，跟隨學姊的腳步而報考音乃木坂學院，聽聞穗乃果要展開偶像活動，便拖著花陽一同加入。\n據花陽所稱，原本在校成績就頗為淒慘，導師評論為若能考上音乃木坂的話堪稱無望，後在花陽拼死拼活的輔導之下奇蹟似的考上學校。";
                    animeText = "除了性格相當有活力之外，對於不熟識之人會收起個性，熟稔後會去親近對方，對不坦率的真姬而言有如天敵般的存在。\n由於思考方向較為直接，因此說話就容易直接表明出狀況跟真相，直來直往的個性讓她常常被真姬或妮可修理一頓，對於模仿他人舉動與說話的風格相當有一套。\n自小學起認識花陽。在第一話登場時，陪著要遲到的花陽去看A-RISE最新的公開影像，後來鼓勵花陽去參加μ's，在二年級成員的勸誘下與真姬同時加入。\n在小學穿裙子時常被男孩子譏笑，而從此認為自己不適合穿裙子、不可愛，高中時也僅穿著過制服規定的裙子，但內心依然有著女孩子性情的她會在家裡自己偷偷對著鏡子試穿洋裝等較有女孩子氣的服裝。\n在第2期第5話因花陽的鼓勵換上裙裝，成為時裝秀Live的Center，在平日的練習服裝也換上了裙子，開始努力讓自己有年輕女孩的魅力出來。";
                    break;
                case "西木野 真姫":
                    romaji = "Nishikino Maki";
                    resID = R.drawable.maki;
                    basics = "15歲，高中1年級生。生日4月19日，白羊座，血型為AB型。身高161厘米，B78/W56/H83。第一人稱為「我」（わたし Watashi）。\n好勝的傲嬌少女。父母是擁有一間綜合醫院的院長夫妻。真姬打算繼承雙親成為腦外科醫生為目標而學習各種相關事物。有15年無男友。[3]";
                    mangaText = "想要進入UTX學園就讀，但因父母強制性的安排而就讀音乃木坂學院，因父母擅自決定此事而十分生氣。\n雙親認為學園偶像是「俗物」，在女兒決定加入μ's後表達出了相當厭惡的態度，曾一度想放棄，後在海未說服其雙親後重新燃起希望。\n一心一意想要轉校到UTX學園，後來與妮可意外碰面、並得知了妮可想要用參加lovelive!大賽的優勝資格、直接入讀UTX的計劃後，與妮可一起加入μ's。";
                    animeText = "被描述為有音樂才華，避免與周圍的人有所關係，因此經常獨自一人；每逢休息時間就到圖書館，放學後就到音樂教室。\n在一次的自彈自唱中被穗乃果看見其音樂才華（第1期第1話）而被招攬，起初雖拒絕，但後來被穗乃果打動心中對音樂的熱情，開始幫μ's作曲，《START:DASH!!》的作曲擔當，之後負責了團體內大部分的音樂作曲。\n因為已經決定好未來的出路，因此將自己對音樂的興趣隱藏起來，後在花陽決定加入μ's時流露出來對偶像、音樂的憧憬一面，才與小凜同時加入μ's。\n在第2期動畫裡被發現還相信著聖誕老人的存在。\n在花陽從妮可承繼偶像研究部部長之後，成為社團的副部長。";
                    break;
                case "矢澤 にこ":
                    romaji = "Yazawa Nico";
                    resID = R.drawable.nico;
                    basics = "17歲，高中3年級生。生日7月22日，巨蟹座，血型為A型。身高154厘米，B74/W57/H79。第一人稱為「妮可」（にこ Nico）。\n雙馬尾。想著能穿著喜歡的各種各樣時尚的衣服而參加偶像活動。口頭禪是「NicoNicoNi-」（にっこにっこにー，香港及大陸小說版用「NicoNicoNi」，網民常譯為「25252」，台灣代理的動畫版字幕及中文版遊戲譯為「微笑小香香」，而台灣角川代理的漫畫版則是用「妮可妮可妮」）。\n雖然官方設定的胸圍是74cm，但正確數值應為71cm。";
                    mangaText = "髮型是豎起自然垂落的雙馬尾，跟東條希的髮型極度相似，在工作場合跟偶像表演活動時才會將雙馬尾綁高，曾在女僕咖啡廳打工。\n外在的形象是聰明伶俐，但也有知性溫柔的一面，原本想入讀UTX學園，但因學費昂貴而選擇進入音乃木坂學園就讀。負責製作μ’s衣服。\n對於偶像娛樂圈的生態跟知識相當透徹，一心一意想當偶像；後來去UTX詢問轉校事宜，才發現了在lovelive!大賽取得優勝就可以就讀UTX學院的優惠規定，與單純想要轉校的真姬不謀而合，二人深談後決定一起聯手參加μ's，藉由此計畫來讓二人共同的戰略目標能夠實現。\n雖然是帶有目的而參加μ's，但是對於團體內的事務一點都不馬虎，相當認真地參與其中。";
                    animeText = "認為偶像是「讓觀眾綻放笑容」的工作。偶像研究社的部長。在一年級時成立偶像研究社，但因為妮可的標準太高，其他成員無法負荷而陸續退出。\n相當關注μ's的行動，認為μ's職業意識不夠，因此要求其解散。後因穗乃果等人的行動而加入μ's，成為第七個加入μ's的成員。\n有兩個妹妹以及一個弟弟，由於弟妹年齡太小加上母親因工作常常不在家，常常是姐代母職照顧弟妹。\n畢業後將偶像研究部部長一職交給花陽。";
                    break;
                case "東條 希":
                    romaji = "Tōjō Nozomi";
                    resID = R.drawable.nozami;
                    basics = "17歲，高中3年級生。生日6月9日，雙子座，血型為O型。身高159厘米，B90/W60/H82。第一人稱為「咱」（うち Uchi）。\n學生會副會長。雙馬尾。\n不是出生於關西，卻說著關西腔。持有占卜抽籤等絕對不會出現失敗結果的超強運，專注於存在自然環境中的精神能量。[3]\n為μ's取名字的命名者，被繪里譽為「創造了μ's的女神大人」。";
                    mangaText = "性格有別於穗乃果的熱情、妮可的伶俐，以無拘無束的自由奔放態度與其他人相處。\n獨自經營著「超自然現象研究會」只有本人參與的一人同好會，但在即將廢校的環境下同好會制度被廢除，聽從繪里的建議將社團與穗乃果的偶像部合併，對穗乃果等人的偶像活動抱持著興趣而加入。\n與穗乃果等人的偶像部合併時，為偶像部帶來了之前同好會的顧問老師當輔導，也有了一個以後她需要進行自然現象相關的活動時，有人能幫忙她就好的協議。\n雖然使用關西腔，但其自稱是在秋葉原土生土長長大的。\n在社團合併前，使用水晶球占卜出了她自己會跟穗乃果等其他人一起面對偶像活動時，會成為一個生死與共、永不分離的命運共同體。";
                    animeText = "擔任學生會副會長的職務。空閒的時候會在神田明神擔任巫女。因自己所占卜出的塔羅牌結果，決定在旁待望守護μ's，有時則會出言幫助。\n小時候因為隨著父親的工作而時常轉學，所以沒有朋友。\n被描繪成一個性格難以捉摸的人，擁有逗趣的舉止，也有溫柔的一面，此外她也是妮可在耍白爛時的天敵。";
                    break;
                case "絢瀬 絵里":
                    romaji = "Ayase Eli";
                    resID = R.drawable.eri;
                    basics = "17歲，高中3年級生。生日10月21日，天秤座，血型為B型。身高162厘米，B88/W60/H84。第一人稱為「我」（わたし Watashi）。\n學生會會長。享樂主義的鮮明性格。從以前到現在從未對任何事專心一意，不過對偶像活動燃起了興趣。祖母為俄羅斯人，小時候被稱為「聰明可愛的繪里」（日語：賢い可愛いエリーチカ Kashikoi Kawaii Elichika）。 為分隊「BiBi」的隊長[3]。口頭禪是「Хорошо!」（俄語：太好了！）。";
                    mangaText = "與穗乃果、小鳥和海未四人是童年玩伴，性格上親切自然、親和力跟責任感十足，在後輩眼中有如大姊、親姐姐般的存在。\n原本沒有參與社團，後來在其他學生的擁護下，擔任學生會長的職務，得知穗乃果成立偶像部的原因後，為支持她們的社團與學校的存亡四處奔走，直到最後才加入為μ's的一員。";
                    animeText = "與穗乃果等2年級生組的人並未認識。擔任學生會會長。雖然向理事長提出停止廢校的對策但被駁回。反對穗乃果等人成立偶像部。原先錄下μ's演唱的第1場Live是為了證明μ's實力不足，但意外讓μ's開始出現人氣。由於自身有練習芭蕾舞，所以不認同μ's的實力，但海未讓其知道她們能做得到的決心，而成功邀請繪里加入μ's。之後才放開心境並推動了團員之間，不用在顧及稱謂與上下的關係，使得團員之間的感情能夠融洽。\n有一日俄混血的妹妹。小時候曾學過芭蕾舞，加入μ's後開始對其他成員進行身體柔軟度的訓練，並與海未一同監督成員的體能狀況，也是團體之後的舞技指導與構想者。\n因就讀學校為祖母高中母校的關係，對學校的重視非同一般。\n第2期將學生會長的職務交給穗乃果；另外在第2期動畫裡流露出怕黑的性格。";
                    break;
                default:
                    romaji = "noname";
                    resID = -1;
                    basics = "error";
                    mangaText = "error";
                    animeText = "error";
                    break;
            }
        }


        public String getRomaji() {
            return romaji;
        }

        public int getResID() {
            return resID;
        }

        public String getBasics() {
            return basics;
        }

        public String getMangaText() {
            return mangaText;
        }

        public String getAnimeText() {
            return animeText;
        }
    }
}