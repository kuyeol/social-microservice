package org.acme.client.ungorithm.composite;

 abstract class AbstractShape implements Shape {
    public int x;
    public int y;
    AbstractShape(int x, int y) {

        this.x = x;
        this.y = y;

    }

     @Override
     public int getX() {
         return x;
     }

     @Override
     public int getY() {
         return y;
     }

     @Override
     public int getWidth() {
         return 0;
     }

     @Override
     public int getHeight() {
         return 0;
     }

     @Override
     public void move(int x, int y) {
         this.x += x;
         this.y += y;
     }

     @Override
     public boolean isInsideBounds(int x, int y) {
         return x > getX() && x < (getX() + getWidth()) &&
                y > getY() && y < (getY() + getHeight());
     }





}
