document.addEventListener( 'DOMContentLoaded', function(){
	/**@type {HTMLCanvasElement} */
	
	const canvas = document.getElementById('canvas');
	const ctx = canvas.getContext('2d');

	
	// fill style
	ctx.fillStyle = 'red';
	// starting point
	let x = 0;
	let y = 0;

		
	//create a draw function to draw each frame
	function draw() {
		//if 'x' is greater than 500px, stop animation
		if(x>500) {
			return;
		}
		if(y>500) {
			return;
		}
		ctx.clearRect(0,0,canvas.width,canvas.height);
		ctx.fillRect(x,y,50,50);
		//increase 'x' value by '5px'
		x += 10;
		y += 10;
		//register 'draw()' call before next paint
		requestAnimationFrame(draw);
	}

	requestAnimationFrame(draw);
	
});
