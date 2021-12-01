document.addEventListener( 'DOMContentLoaded', function(){
	/**@type { HTMLCanvasElement } */
	const canvas = document.getElementById('canvas');
	const ctx = canvas.getContext( '2d' );
	console.log(ctx);
	//check width and height of the canvas
	console.log('dimensions', canvas.width, canvas.height);
	
	//verify canvas DOM node
	console.log(canvas instanceof HTMLCanvasElement);
	
	//verify rendering context interface
	console.log(ctx instanceof CanvasRenderingContext2D);
	
	
})