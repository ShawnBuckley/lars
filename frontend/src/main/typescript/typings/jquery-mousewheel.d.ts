/// <reference path="jquery/jquery.d.ts" />

interface JQueryMousewheelEventObject extends JQueryEventObject {
    deltaX: number;
    deltaY: number;
}