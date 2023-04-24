import { IsNotEmpty, IsNumber, IsPositive } from "class-validator";

export default class BookDto {
  @IsNotEmpty()
  title: string;

  @IsNotEmpty()
  author: string;

  @IsNumber()
  @IsPositive()
  publish_year: number;

  @IsNumber()
  @IsPositive()
  page_count: number;
}
