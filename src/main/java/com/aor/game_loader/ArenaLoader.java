package com.aor.game_loader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aor.game.Arena;
import com.aor.elements.*;
import com.aor.game.GenericArena;
import com.aor.game_loader.loaders.*;
import com.aor.hero.mvc.Hero;
import com.aor.tile.Tile;
import com.aor.utils.Vector2d;

public class ArenaLoader {
    Map<Character,GameElementLoader> loader = Map.of(
            'T', new TileLoader() ,
            'E', new EndBlockLoader(),
            'S', new GroundedSpikeLoader(),
            'V', new GroundedVoidBlockLoader(),
            'B', new StraberryLoader(),
            'H', new HeroLoader(),
            'F', new BackgroundLoader(),
            'G', new GrassLoader(),
            'C', new FlowerLoader(),
            'A', new TreeLoader());

    public <T> List<T> convert(List<GameElement> elements){
        List<T> res= new ArrayList<>();
        for (GameElement e : elements) {
            res.add((T) e);
        }
        return res ;
    }

    public  List<GameElement> load16x16File(String filePath, Character id) throws IOException {
        List<GameElement> res = new ArrayList<>();
        DataInputStream data = null;

        try {
            data = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            StringBuilder stringBuilder = new StringBuilder();

            boolean possible = true;
            while (possible) {
                try {
                    Byte character = data.readByte();
                    char c = (char) character.byteValue();
                    if (c != '\n' && c != '\r') {
                        stringBuilder.append(c);
                    }
                } catch (Exception e) {
                    possible = false;
                }
            }
            String stringData = stringBuilder.toString();
            String[] elementData = stringData.split(",");
            int counter = 0;

            for (String elementInfo : elementData) {
                elementInfo = elementInfo.trim();
                if (!(elementInfo.isEmpty()) && elementInfo.charAt(0) == id) {
                    Vector2d pos = new Vector2d((int)(counter % 16d) * 8d,  (int) (counter / 16d) * 8d);
                    res.add(loader.get(id).parse(elementInfo.substring(1), pos));
                }
                counter++;
            }

        } finally {
            assert data != null;
            data.close();
        }

        return res;
    }
    public List<Tile> loadTile16x16file(String filePath) throws IOException {
        return this.convert(load16x16File(filePath, 'T'));
    }
    public List<EndBlock> loadEndBlock16x16file(String filePath) throws IOException {
        return this.convert(load16x16File(filePath, 'E'));
    }

    public List<GroundedSpike> loadSpike16x16file(String filePath) throws IOException {
        return this.convert(load16x16File(filePath, 'S'));
    }

    public List<GroundVoidBlock> loadVoid16x16file(String filePath) throws IOException {
        return this.convert(load16x16File(filePath, 'V'));
    }

    public List<Strawberry> loadStrawberry16x16file(String filePath) throws IOException {
        return this.convert(load16x16File(filePath, 'B'));
    }

    public List<Hero> loadHero16x16file(String filePath, GenericArena arena) throws IOException {
        ((HeroLoader)loader.get('H')).setArena(arena);
        return this.convert(load16x16File(filePath, 'H'));
    }

    public List<BackgroundBlock> loadBackground16x16file(String filePath) throws IOException {
        List<BackgroundBlock> tiles = new ArrayList<>();
        tiles.addAll(convert(load16x16File(filePath, 'F')));
        tiles.addAll(convert(load16x16File(filePath, 'G')));
        tiles.addAll(convert(load16x16File(filePath, 'C')));
        tiles.addAll(convert(load16x16File(filePath, 'A')));

        return tiles;
    }

    public Arena arenaBuilder(String filePath, String backgroundPath) throws IOException {
        List<Tile> tiles=loadTile16x16file(filePath);
        List<Strawberry> strawberries=loadStrawberry16x16file(filePath);
        List<EndBlock> endBlocks=loadEndBlock16x16file(filePath);
        List<GroundedSpike> spikes=loadSpike16x16file(filePath);

        List<GroundVoidBlock> voids=loadVoid16x16file(filePath);
        List<BackgroundBlock> background = loadBackground16x16file(backgroundPath);

        Arena arena = new Arena(null, tiles, strawberries, endBlocks, spikes, voids, background);

        List<Hero> heroes = loadHero16x16file(filePath, arena);
        arena.setHero(heroes.getFirst());
        return arena;
    }
}
