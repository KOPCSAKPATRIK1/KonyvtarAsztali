import {
  Column,
  Entity,
  JoinColumn,
  OneToOne,
  PrimaryGeneratedColumn,
} from 'typeorm';
import { Books } from './books.entity';

@Entity()
export class Rentals {
  @PrimaryGeneratedColumn()
  id: number;

  @OneToOne(() => Books)
  @JoinColumn({ name: 'book_id' })
  book_id: number;

  @Column()
  start_date: Date;

  @Column()
  end_date: Date;
}
