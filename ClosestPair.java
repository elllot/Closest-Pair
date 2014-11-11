package lab1;

import java.util.ArrayList;
import java.util.Arrays;

public class ClosestPair {

    public final static double INF = java.lang.Double.POSITIVE_INFINITY;

    /** 
     * Given a collection of points, find the closest pair of point and the
     * distance between them in the form "(x1, y1) (x2, y2) distance"
     *
     * @param pointsByX points sorted in nondecreasing order by X coordinate
     * @param pointsByY points sorted in nondecreasing order by Y coordinate
     * @return Result object containing the points and distance
     */
    static Result findClosestPair(XYPoint pointsByX[], XYPoint pointsByY[]) {
       // YOUR CODE GOES HERE
        int size = pointsByX.length;
        int mid = size/2 + 1;
        XYPoint xL[] = new XYPoint[mid];
        XYPoint xR[] = new XYPoint[size-mid];
        XYPoint yL[] = new XYPoint[mid];
        XYPoint yR[] = new XYPoint[size-mid];

        if (pointsByX.length < 2){
            Result rMin = new Result(pointsByX[0], pointsByX[0], INF);
            return rMin;
        }
        else if (pointsByX.length == 2){
            double d = pointsByX[0].dist(pointsByX[1]);
            Result r = new Result(pointsByX[0], pointsByX[1], d);
            return r;
        }
        else if (pointsByX.length == 3){
            Result dMin = new Result(pointsByX[0], pointsByX[1], pointsByX[0].dist(pointsByX[1]));
            Result d12 = new Result(pointsByX[1], pointsByX[2], pointsByX[1].dist(pointsByX[2]));
            Result d02 = new Result(pointsByX[0], pointsByX[2], pointsByX[0].dist(pointsByX[2]));
            dMin = min(dMin, d12);
            dMin = min(dMin, d02);
            return dMin;
        }
        else{
            for (int i = 0; i < mid ; i++){
                xL[i] = pointsByX[i];
                //yL[i] = pointsByX[i];
            }
            for (int j = 0; j < size-mid; j++){
                xR[j] = pointsByX[j + mid];
                //yR[j] = pointsByX[j + mid];
            }

            // tried using msort to sort xL and xR in order of but that would be 2nlogn again
            /*YComparator lessThanY = new YComparator();

            Sort.msort(yL, lessThanY);
            Sort.msort(yR, lessThanY);*/


            //this method gives us (n/2 * 2) = n or worst case scenario of 2n compared to the 2nlogn from 2 msorts
            int count = 0;
            for (int k = 0; k < size; k++){
                if (count < yL.length){
                    if (pointsByY[k].isLeftOf(pointsByX[mid])){
                    yL[count] = pointsByY[k];
                    count++;
                    }
                }
                else{
                    break;
                }
            }

            count = 0;
            for (int l = 0; l < size; l++){
                if (count < yR.length){
                    if(!pointsByY[l].isLeftOf(pointsByX[mid])){
                    yR[count] = pointsByY[l];
                    count++;
                    }
                }
                else {
                    break;
                }
            }
        }
          
        

        Result r1 = ClosestPair.findClosestPair(xL, yL);
        Result r2 = ClosestPair.findClosestPair(xR, yR);
        XYPoint midPoint[] = new XYPoint[1];
        midPoint[0] = pointsByX[mid];

        if (min(r1, r2) == r1){
            Result r3 = combine(pointsByY, midPoint, r1);
            if (r3.p1.x > r3.p2.x) {
                Result rFin = new Result(r3.p2, r3.p1, r3.dist);
                return rFin;
            }
            else if (r3.p1.x == r3.p2.x && r3.p1.y > r3.p2.y){
                Result rFin = new Result(r3.p2, r3.p1, r3.dist);
                return rFin;
            }
            else {
                return r3;
            }
        }
        else{
            Result r3 = combine(pointsByY, midPoint, r2);
             if (r3.p1.x > r3.p2.x) {
                Result rFin = new Result(r3.p2, r3.p1, r3.dist);
                return rFin;
            }
            else if (r3.p1.x == r3.p2.x && r3.p1.y > r3.p2.y){
                Result rFin = new Result(r3.p2, r3.p1, r3.dist);
                return rFin;
            }
            else {
                return r3;
            }
        }

    }

    static Result combine(XYPoint pByY[], XYPoint pByX[], Result r){
        ArrayList<XYPoint> yStrip = new ArrayList<XYPoint>();
        int mid = pByX[0].x;
        for (int y = 0; y < pByY.length; y++){
            if(abs(mid - pByY[y].x) <= r.dist) {
                yStrip.add(pByY[y]);
            }
        }
        for (int j = 0 ; j < yStrip.size() - 1; j++){
            int k = j + 1;
            while(k <= yStrip.size()-1 && yStrip.get(k).y - yStrip.get(j).y < r.dist){
                if(yStrip.get(k).dist(yStrip.get(j)) < r.dist){
                    double d = yStrip.get(k).dist(yStrip.get(j));
                    r = new Result(yStrip.get(k), yStrip.get(j), d);
                }
                else if(yStrip.get(k).dist(yStrip.get(j)) == r.dist){
                    if (yStrip.get(k).x < r.p1.x){
                        r =  new Result(yStrip.get(k), yStrip.get(j), yStrip.get(k).dist(yStrip.get(j)));
                    }
                }
                k++;
            }
        }
        return r;
    } 


    
    static int abs(int x) {
        if (x < 0) {
            return -x;
        } else {
            return x;
        }
    }
    static Result min(Result a, Result b){
        if (a.dist < b.dist){
            return a;
        }
        else if (a.dist > b.dist){
            return b;
        }
        else{
            if (a.p1.x < b.p1.x){
                return a;
            }
            else if (a.p1.x > b.p1.x){
                return b;
            }
            else if (a.p1.y < b.p1.y){
                return a;
            }
            else if (a.p1.y > b.p1.y){
                return b;
            }
            else {
                return a;
            }
        }
    }

    static Result findClosestPairBruteForce(XYPoint points[]){
        Result rMin = new Result(points[0], points[0], INF);
        for (int i = 0; i < points.length; i++){
            for (int j = i + 1; j < points.length; j++){
                double comp = points[i].dist(points[j]);
                if( comp < rMin.dist ){
                    if (points[i].x < points[j].x){
                        rMin = new Result(points[i], points[j], comp);
                    }
                    else if (points[i].x > points[j].x){
                        rMin = new Result(points[j], points[i], comp);
                    }
                    else if (points[i].x == points[j].x && points[i].y < points[j].y){
                        rMin = new Result(points[i], points[j], comp);
                    }
                    else {
                        rMin = new Result(points[j], points[i], comp);
                    }
                }
                else if(comp == rMin.dist){
                    if (points[i].x < rMin.p1.x){
                        rMin = new Result(points[j], points[i], comp);
                    }
                }
            }
        }
        return rMin;
    } 
}
