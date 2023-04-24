import {
  Body,
  ConflictException,
  Controller,
  Get,
  HttpException,
  NotFoundException,
  Param,
  Post,
  Render,
} from '@nestjs/common';
import { DataSource } from 'typeorm';
import { AppService } from './app.service';
import { Rentals } from './entities/rentals.entity';
import { Books } from './entities/books.entity';
import { faker } from '@faker-js/faker';
import BookDto from './dtos/book.dto';

@Controller()
export class AppController {
  constructor(
    private readonly appService: AppService,
    private dataSource: DataSource,
  ) {}

  @Get()
  @Render('index')
  index() {
    return { message: 'Welcome to the homepage' };
  }

  @Post('/seed')
  async seed() {
    const rentalsRep = this.dataSource.getRepository(Rentals);

    for (let i = 1; i < 16; i++) {
      const rental = new Rentals();
      rental.book_id = i;
      rental.start_date = faker.date.past();
      rental.end_date = faker.date.future();
      await rentalsRep.save(rental);
    }
  }

  @Get('api/books')
  async getBooks() {
    const booksRep = this.dataSource.getRepository(Books);
    return booksRep.find();
  }

  @Post('api/books')
  async addBook(@Body() bookDto: BookDto) {
    const booksRep = this.dataSource.getRepository(Books);
    const book = new Books();

    book.title = bookDto.title;
    book.author = bookDto.author;
    book.pageCount = bookDto.page_count;
    book.publishYear = bookDto.publish_year;

    await booksRep.save(book);
  }

  @Post('api/books/:id/rent')
  async rent(@Param('id') bookId: number) {
    const rentalsRep = this.dataSource.getRepository(Rentals);

    if (
      (await rentalsRep
        .createQueryBuilder('rentals')
        .where('rentals.book_id = :bookId', { bookId })
        .getExists()) == true
    ) {
      throw new ConflictException();
    }

    if (
      (await this.dataSource.getRepository(Books).findOneBy({ id: bookId })) ==
      null
    ) {
      throw new NotFoundException();
    }

    const rent = new Rentals();
    rent.book_id = bookId;
    rent.start_date = new Date();
    rent.end_date = rent.start_date;
    rent.end_date.setDate(rent.start_date.getDate() + 7);

    rentalsRep.save(rent);
    return rent;
  }
}
