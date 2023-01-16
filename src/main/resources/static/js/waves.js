const canvas = document.getElementById("canvas")
const concerts = document.getElementsByClassName("concert")
const width = 500
const height = 600

canvas.width = width
canvas.height = height
const ctx = canvas.getContext("2d")
// ctx.translate(-50, -40)
ctx.translate(0, 500)

// https://stackoverflow.com/a/8831937/18337551
function hashArray(str) {
    let hash = 0;
    for (let i = 0, len = str.length; i < len; i++) {
        let chr = str.charCodeAt(i);
        hash = (hash << 5) - hash + chr;
        hash |= 0; // Convert to 32bit integer
    }
    hash = Math.abs(hash * 245256)
    return Array.from(hash.toString()).map(value => Number(value));
}

// https://stackoverflow.com/a/23095818/18337551
function randomRgba() {
    var o = Math.round, r = Math.random, s = 255;
    return 'rgba(' + o(r()*s) + ',' + o(r()*s) + ',' + o(r()*s) + ',' + r().toFixed(1) + ')';
}

function smallerValue(value1, value2){
    if(value1 < value2) return value1
    if(value1 > value2) return value2
    return value1 + 2
}

/**
 *
 * naprzemienne trójkąty
 */


function generatePoints(array){
    const spacing = 50
    const output = []


    for(let i=0; i<11; i+=2){

        const pointLeftY = array[i]
        let pointCenterY = array[i+1]
        const pointRightY = array[i+2]

        // console.log(i)
        // console.log(i+1)
        // console.log(i+2)

        const value = smallerValue(pointLeftY, pointRightY)

        if(i%4 == 0){
            // down
            pointCenterY = value - (pointLeftY + pointRightY) / 2

        }else if(i%2 == 0 && i != 0){
            pointCenterY = value + (pointLeftY + pointRightY) / 2
        }


        // if(i % 4 == 0){
        //     console.log("down")
        //     if(pointCenterY - pointLeftY > 0) pointCenterY -= pointCenterY + pointLeftY
        //     if(pointCenterY - pointRightY > 0) pointCenterY -= pointCenterY + pointRightY
        //     if(pointCenterY == 0 && pointLeftY == 0) pointCenterY -= pointRightY
        //     if(pointCenterY == 0 && pointRightY == 0) pointCenterY -= pointLeftY
        // }else if(i % 2 == 0 && i != 0){
        //     console.log("up")
        //     if(pointCenterY - pointRightY <= 0) pointCenterY += pointRightY
        //     if(pointCenterY - pointLeftY <= 0) pointCenterY += pointLeftY
        //     if(pointCenterY == 0 && pointLeftY == 0) pointCenterY += pointRightY
        //     if(pointCenterY == 0 && pointRightY == 0) pointCenterY += pointLeftY
        // }



        output.push({x: i * spacing, y: pointLeftY * 40})
        output.push({x: (i+1) * spacing, y: pointCenterY * 40})


    }


    return output
}



function drawPoint(point){
    ctx.fillStyle = "black"
    ctx.fillRect(point.x, -point.y , 10, 10)
}

function drawCurve(point1, point2, point3){
    ctx.beginPath()
    ctx.moveTo(point1.x, -point1.y)

    // ctx.bezierCurveTo(point1.x, point1.y * 2,)
    // ctx.lineTo(point2.x, -point2.y)
    ctx.quadraticCurveTo(point2.x, -1.1*point2.y, point3.x, -point3.y)
    ctx.lineTo(point3.x, 300)
    ctx.lineTo(point1.x, 300)
    ctx.lineTo(point1.x, -point1.y)
    // ctx.stroke()
    ctx.fillStyle = "black"
    ctx.fill()
}



const arr = hashArray(concerts[0].id)
            const points = generatePoints(arr)
            for(let i=0; i<points.length - 2; i+=2){
                drawCurve(points[i], points[i+1], points[i+2])
            }



let toogled = ""

for(const concert of concerts){
    concert.addEventListener("mouseover", e => {
        if(toogled !== concert.id){
            toogled = concert.id
            ctx.clearRect(0, -500, 1000, 1000)
            const arr = hashArray(concert.id)
            const points = generatePoints(arr)
            for(let i=0; i<points.length - 2; i+=2){
                drawCurve(points[i], points[i+1], points[i+2])
            }


        }

    })
}





