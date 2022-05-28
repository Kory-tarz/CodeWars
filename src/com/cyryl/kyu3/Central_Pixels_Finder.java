package com.cyryl.kyu3;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.*;

public class Central_Pixels_Finder extends Image
{

    protected int[] modPixels;
    protected int[] pixels;
    protected int width;
    protected int height;

    public int[] central_pixels(int colour){

        int maxDist;
        modPixels = new int[pixels.length];

        searchTopBottom(colour);
        printModPixels();
        maxDist = searchBottomTop(colour);
        printModPixels();

        return getMaxPosArr(maxDist);
    }

    private void printModPixels(){
        for(int i = 0; i< modPixels.length; i+=width) {
            for (int j = 0; j < width; j++)
                System.out.print(modPixels[i + j] + " ");
            System.out.println();
        }
        System.out.println("------------------");
    }

    private int[] getMaxPosArr(int maxDist) {
        if(maxDist == 0)
            return (new int[0]);

        List<Integer> list = new ArrayList<>();
        for(int i = 0; i< modPixels.length; i++)
            if(modPixels[i] == maxDist)
                list.add(i);

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    private int searchBottomTop(int colour) {
        int max = 0;
        for(int i = pixels.length-1; i>=0; i--)
            if(pixels[i] == colour) {
                setPixelFromBottom(i, colour);
                max = Math.max(modPixels[i], max);
            }
        return max;
    }

    private void searchTopBottom(int colour) {
        for(int i = 0; i< pixels.length; i++){
            if(pixels[i] == colour)
                setPixelFromTop(i, colour);
        }
    }

    private void setPixelFromBottom(int pos, int colour) {
        int dist;
        int tempPos;

        dist = modPixels[pos];

        tempPos = getPixelUp(pos);
        setDistance(tempPos, colour, dist);

        tempPos = getPixelLeft(pos);
        setDistance(tempPos, colour, dist);
    }

    private void setPixelFromTop(int pos, int colour) {

        int dist;
        int tempPos;

        dist = minDistAround(pos, colour);
        modPixels[pos] = dist;

        tempPos = getPixelDown(pos);
        setDistance(tempPos, colour, dist);

        tempPos = getPixelRight(pos);
        setDistance(tempPos, colour, dist);
    }

    private void setDistance(int tempPos, int colour, int dist) {
        if(tempPos != -1 && pixels[tempPos] == colour) {
            if (modPixels[tempPos] == 0)
                modPixels[tempPos] = dist + 1;
            else
                modPixels[tempPos] = Math.min(dist + 1, modPixels[tempPos]);
        }
    }

    private int minDistAround(int pos, int colour){
        int otherPos;

        otherPos = getPixelUp(pos);
        if(otherPos == -1 || pixels[otherPos] != colour)
            return 1; // edge found

        otherPos = getPixelRight(pos);
        if(otherPos == -1 || pixels[otherPos] != colour)
            return 1;

        otherPos = getPixelDown(pos);
        if(otherPos == -1 || pixels[otherPos] != colour)
            return 1;

        otherPos = getPixelLeft(pos);
        if(otherPos == -1 || pixels[otherPos] != colour)
            return 1;

        return modPixels[pos];
    }

    private int getPixelUp(int pos){
        int up = pos - width;
        return up > 0 ? up : -1;
    }

    private int getPixelDown(int pos){
        int down = pos + width;
        return down < modPixels.length ? down : -1;
    }

    private int getPixelLeft(int pos){
        if(pos%width == 0)
            return -1;
        return pos-1;
    }

    private int getPixelRight(int pos){
        if(pos%width == width-1)
            return -1;
        return pos+1;
    }

    protected void set(int width, int height, int[] imageData){
        pixels = imageData.clone();
        this.width = width;
        this.height = height;
    }

    protected void resize(int newWidth, int newHeight){
        width = newWidth;
        height = newHeight;
        pixels = new int[width * height];
    }

    @Override
    public int getWidth(ImageObserver observer) {
        return width;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return height;
    }

    @Override
    public ImageProducer getSource() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return null;
    }
}
