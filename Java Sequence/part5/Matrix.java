public class Matrix extends Sequence{
	private int rowsize;
	private int colsize;
	private int[][] matrix;
	public void Set(int rowsize, int colsize, int value){ matrix[rowsize][colsize] = value; }
	public int Get(int rowsize, int colsize){	return this.matrix[rowsize][colsize]; 		}
	public Matrix(int rowsize, int colsize){
		this.rowsize = rowsize;
		this.colsize = colsize;
		matrix = new int[rowsize][colsize];
		for (int i = 0; i < rowsize; i++)
			for (int j = 0; j < colsize; j++)
				matrix[i][j] = 0;
	}
	public Matrix Sum(Matrix mat){
		Matrix newMatrix = new Matrix(rowsize, colsize);
		for(int i = 0; i <rowsize; i++)
			for(int j = 0; j < colsize; j++)
				newMatrix.matrix[i][j] = this.matrix[i][j] + mat.matrix[i][j];
		return newMatrix;
	}
	public Matrix Product(Matrix mat){
		Matrix newMatrix = new Matrix(rowsize, mat.colsize);
		if(this.colsize==mat.rowsize){
		  for(int i=0; i<this.rowsize; i++){
			  	for(int j=0; j<mat.colsize; j++){  
					int sum = 0;
				 	for(int k=0; k<this.colsize; k++)
						sum = sum + this.matrix[i][k]*mat.matrix[k][j];
					newMatrix.Set(i,j,sum);
				}
			}	
			return newMatrix;
		}else{
			System.out.println("Matrix dimensions incompatible for Product");
			System.exit(1);
		}
		return null;
	}
	public void Print(){
		for (int i = 0; i < rowsize; i++){
			System.out.print("[ ");
			for (int j = 0; j < colsize; j++){
				System.out.print(matrix[i][j]);
				System.out.print(" ");
			}
			System.out.println("]");
		}
	}
}
