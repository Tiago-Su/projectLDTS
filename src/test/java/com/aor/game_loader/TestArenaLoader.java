package com.aor.game_loader;

import com.aor.elements.*;
import com.aor.game.GameData;
import com.aor.game.GenericArena;
import com.aor.hero.mvc.Hero;
import com.aor.tile.Tile;
import com.aor.utils.Vector2d;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

interface GameElementGenerator{
    GameElement gen(String data,Vector2d pos) throws IOException;
}

class TestArenaLoader {
    String testLoaderFilename="src/test/java/com/aor/game_loader/TestLoaderFile.txt";
    ArenaLoader arenaLoader;
    GenericArena arena;
    Map<String,String> tileImagePaths= GameData.getTileImgPath();
    List<String> tileCodes;
    List<String> spikeCodes;
    List<String> endBlockCodes;

    List<String> groundedVoidBlockCodes;
    List<String> strawberryCodes;
    List<String> heroCodes;

    List<String> backgroundCodes;

    Map<String,GameElementGenerator> backgroundMaker;

    boolean setupOnce=false;
    @BeforeEach
    void setup(){
        if(!setupOnce) {
            tileCodes = new ArrayList<>();
            backgroundCodes= new ArrayList<>();
            backgroundCodes.addAll(List.of("G00","G01","A00","A01","C"));

            endBlockCodes=List.of("E");
            spikeCodes=List.of("S");

            groundedVoidBlockCodes=List.of("V");
            strawberryCodes=List.of("B");
            heroCodes=List.of("H");

            backgroundMaker=new HashMap<>();
            backgroundMaker.put("F", (String data, Vector2d pos)->new BackgroundBlock(pos,tileImagePaths.get(data)));
            backgroundMaker.put("G",(String data, Vector2d pos)->new BackgroundBlock(pos, (data.equals("G01")?new Vector2d(1, 4):new Vector2d(1, 2)), 6, (data.equals("G01")?4:6), GameData.getTileImgPath().get(data)));

            backgroundMaker.put("A",(String data, Vector2d pos)->new BackgroundBlock(pos,tileImagePaths.get(data)));
            backgroundMaker.put("C",(String data, Vector2d pos)-> new BackgroundBlock(pos, new Vector2d(1, 1), 5, 7, GameData.getTileImgPath().get("flower")) );


            arenaLoader = new ArenaLoader();
            arena= Mockito.mock(GenericArena.class);

            for (String s : tileImagePaths.keySet()) {
                if (s.startsWith("T")) {
                    tileCodes.add(s);
                }
                if (s.startsWith("F")) {
                    backgroundCodes.add(s);
                }

            }
            setupOnce=true;
        }
    }




    List<GameElement> ExpectedGameElementListAndFileGenerator(int seed,GameElementGenerator generator,List<String> elementCodes) throws IOException {
        List<GameElement> gameElements=new ArrayList<>();


        File file=new File(testLoaderFilename);
        if(!file.exists()){
            file.createNewFile();
            file.setReadable(true);
            file.setExecutable(true);
        }
//List<String>
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(testLoaderFilename));
        Random random=new Random();
        random.setSeed(seed);
        StringBuilder encodingStringBuilder=new StringBuilder();

        //in case of hero, we just want to generate it once
        boolean endEarly=false;
        for(int j=0;j<16 ;j++){
            for(int i=0;i<16 ;i++){
                int randomId=random.nextInt();
                if(randomId>=0  && !endEarly) {
                    randomId%=elementCodes.size();
                    String elementCode=elementCodes.get(randomId);
                    gameElements.add(generator.gen(elementCode,new Vector2d(i * 8, j * 8)));

                    encodingStringBuilder.append(elementCode+",");
                    if(elementCodes.contains("H")){
                        endEarly=true;
                    }

                }
                else {
                    encodingStringBuilder.append("   ,");
                }

            }
            encodingStringBuilder.append("\n");
        }
        String encoingString=encodingStringBuilder.toString();
        bufferedWriter.write(encoingString);
        bufferedWriter.flush();
        bufferedWriter.close();


        return gameElements;
    }

    public <T> List<T> convert(List<GameElement> elements){
        List<T> res= new ArrayList<>();
        for (GameElement e : elements) {
            res.add((T) e);
        }
        return res ;
    }

    @Property(tries=10)
    void TestTileLoader(@ForAll int seed ) throws IOException {
        setup();

        List<Tile> expectedTiles = convert(ExpectedGameElementListAndFileGenerator(seed,(String data,Vector2d pos)->new Tile(pos, tileImagePaths.get(data), GameData.TILE_TAG),tileCodes));
        List<Tile> tiles= arenaLoader.loadTile16x16file(testLoaderFilename);
         Assertions.assertEquals( tiles,expectedTiles);
    }

    @Property(tries=10)
    void TestGroundedSpikeLoader(@ForAll int seed ) throws IOException {
        setup();
        List<GroundedSpike> expectedSpikes = convert(ExpectedGameElementListAndFileGenerator(seed,(String data, Vector2d pos)->new GroundedSpike(pos),spikeCodes));
        List<GroundedSpike> spikes= arenaLoader.loadSpike16x16file(testLoaderFilename);
        Assertions.assertEquals( spikes,expectedSpikes);
    }

    @Property(tries=10)
    void TestGroundedVoidBlockLoader(@ForAll int seed ) throws IOException {
        setup();
        List<GroundVoidBlock> expectedGroundedVoidBlocks = convert(ExpectedGameElementListAndFileGenerator(seed,(String data, Vector2d pos)->new GroundVoidBlock(pos),groundedVoidBlockCodes));
        List<GroundVoidBlock> groundedVoidBlocks= arenaLoader.loadVoid16x16file(testLoaderFilename);
        Assertions.assertEquals( groundedVoidBlocks,expectedGroundedVoidBlocks);
    }

    @Property(tries=10)
    void TestStrawberryLoader(@ForAll int seed ) throws IOException {
        setup();
        List<Strawberry> expectedStrawberries = convert(ExpectedGameElementListAndFileGenerator(seed,(String data, Vector2d pos)->new Strawberry(pos),strawberryCodes));
        List<Strawberry> strawberries= arenaLoader.loadStrawberry16x16file(testLoaderFilename);
        Assertions.assertEquals( strawberries,expectedStrawberries);
    }

    @Property(tries=10)
    void TestHeroLoader(@ForAll int seed ) throws IOException {
        setup();
        List<Hero> expectedHeros = convert(ExpectedGameElementListAndFileGenerator(seed,(String data, Vector2d pos)->new Hero(pos,arena),heroCodes));
        List<Hero> heros= arenaLoader.loadHero16x16file(testLoaderFilename,arena);
        Assertions.assertEquals(heros, expectedHeros);
    }

    @Property(tries=10)
    void TestBackgroundLoader(@ForAll int seed ) throws IOException {
        setup();
        List<BackgroundBlock> expectedBackground = convert(ExpectedGameElementListAndFileGenerator(seed,(String data, Vector2d pos)->backgroundMaker.get(data.substring(0,1)).gen(data,pos),backgroundCodes));
        List<BackgroundBlock> background= arenaLoader.loadBackground16x16file(testLoaderFilename);
        Assertions.assertEquals(new HashSet<>(background),new HashSet<>(expectedBackground));
    }



}
